@Post "/user/{userId}/draw-times/{drawTimes}" is to create/set user's number of lottery attempts.
- "userId" can be any integer.
- "drawTiems" can be any integer.
- E.g. http://localhost:8080/lottery/user/1/draw-times/10
  - I have set userId = 1 and lottery attempts available to 10

@Post "/draw/{userId}" is to make a lottery draw.
E.g. http://localhost:8080/lottery/draw/1

@Get "/userDrawTimes/{userId}" is to retrieve the user's remaining lottery attempts.
E.g. http://localhost:8080/lottery/userDrawTimes/1

@Post "/addPrize" is to add new prizes into the list of prizes available.
The object created called "Prize" and it consists of 4 variables.
- "id" can be any integer.
- "name" can be any String.
- "quantity" can be any integer.
- "winningProbability" can be any double.
E.g. 
{
  "id": "1",
  "name": "iPhone",
  "quantity": 10,
  "winningProbability": 0.1
}

@Get "/prizes" is to list down all the prizes available.
E.g. http://localhost:8080/lottery/prizes

@Get "/userPrizes/{userId}" is to retrieve all the prizes the user has won.
E.g. http://localhost:8080/lottery/userPrizes/1

In LotteryService.java, I have used "Boolean isSet = c(userSubmissionKey, "submitted");" to check for duplicate submissions.
 
