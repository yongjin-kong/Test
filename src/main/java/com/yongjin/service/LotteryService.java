package com.yongjin.service;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import com.yongjin.common.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class LotteryService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedisTemplate<String, String> redisTemplate2;

    @Autowired
    private DefaultMQProducer producer;

    private static final String USER_SUBMISSION_KEY_PREFIX = "submission:user:";
    private static final long SUBMISSION_EXPIRATION_IN_MINUTES = 5;

    public String lotteryDraw(int userId) {
        String userDrawKey = "user_lottery_draw_times:" + userId;
        Integer userDrawTimes = (Integer) redisTemplate.opsForValue().get(userDrawKey);

        String userSubmissionKey = USER_SUBMISSION_KEY_PREFIX + userId;
        // Check if the user has attempt the lottery
        Boolean isSet = redisTemplate.opsForValue().setIfAbsent(userSubmissionKey, "submitted");

        // If the submission is a duplicate
        if (isSet != null && !isSet) {
            return "Duplicate submission detected. You have already participated in this lottery.";
        }

        // Set expiration for the user submission key to prevent indefinite storage
        redisTemplate.expire(userSubmissionKey, SUBMISSION_EXPIRATION_IN_MINUTES, TimeUnit.MINUTES);

        if (userDrawTimes == null || userDrawTimes <= 0) {
            return "Insufficient lottery attempts.";
        }

        List<Object> prizes = redisTemplate.opsForList().range("prizes", 0, -1);
        double totalProbability = prizes.stream().mapToDouble(p -> ((Prize) p).getWinningProbability()).sum();

        double drawResult = Math.random() * totalProbability;
        double current = 0;

        for (Object prizeObj : prizes) {
            Prize prize = (Prize) prizeObj;
            current += prize.getWinningProbability();
            if (drawResult <= current && prize.getQuantity() > 0) {
                prize.setQuantity(prize.getQuantity() - 1);
                redisTemplate.opsForList().set("prizes", prizes.indexOf(prizeObj), prize);

                redisTemplate.opsForValue().decrement(userDrawKey);

                Message message = new Message("DrawResult", "draw", String.valueOf(userId),
                        String.format("{\"userId\":%d,\"prizeId\":%d}", userId, prize.getId()).getBytes());
                try {
                    producer.send(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                storeUserWonPrize(userId, prize.getName());

                return "Congratulations! You have won the following prize(s): " + prize.getName();
            }
        }
        return "Sorry you did not win any prize this time, please try again next time.";
    }

    public void setUserDrawTimes(int userId, int drawTimes) {
        redisTemplate.opsForValue().set("user_lottery_draw_times:" + userId, drawTimes);
    }

    public void addPrize(Prize prize) {
        redisTemplate.opsForList().rightPush("prizes", prize);
    }

    public List<Object> getPrizes() {
        return redisTemplate.opsForList().range("prizes", 0, -1);
    }

    public Set<String> getUserPrizes(int userId) {
        String userPrizesKey = "user_won_prizes:" + userId;
        return redisTemplate2.opsForSet().members(userPrizesKey);
    }

    public int getUserDrawTimes(int userId) {
        String userDrawKey = "user_lottery_draw_times:" + userId;
        Integer userDrawTimes = (Integer) redisTemplate.opsForValue().get(userDrawKey);
        return userDrawTimes != null ? userDrawTimes : 0;
    }

    public void storeUserWonPrize(int userId, String prizeName) {
        String key = "user_won_prizes:" + userId;
        redisTemplate.opsForSet().add(key, prizeName);
    }
}
