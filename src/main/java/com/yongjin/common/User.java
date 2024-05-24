package com.yongjin.common;

public class User {
    private int id;
    private int lotteryAttempts;

    public User(int id, int lotteryAttempts) {
        this.id = id;
        this.lotteryAttempts = lotteryAttempts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLotteryAttempts(int lotteryAttempts) {
        this.lotteryAttempts = lotteryAttempts;
    }

    public int getLotteryAttempts() {
        return lotteryAttempts;
    }
}
