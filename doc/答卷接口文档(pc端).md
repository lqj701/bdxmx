## 学生答卷接口文档(PC端)

### V1.0.0

| 撰写人 |   日期    | 内容 | 审核人 |  版本  |
| :----: | :-------: | :--: | :----: | :----: |
| 李强进 | 2018/3/5 | 初版 | 李强进 | V1.0.0 |



## 目录

- 学生答卷接口文档
    - [1. 获取答卷列表](#1-获取答卷列表)
    - [2. 获取答卷详情](#2-获取答卷详情)
    - [3. 批改答卷](#3-批改答卷)


### 1. 获取答卷列表
| 接口地址     | /api/pc/getAnswerSheets/{studentId} |
| ------------ | ---------------------------------- |
| **请求方法** | GET                    |
| **详细说明** | 获取答卷列表 |



#### 接口响应
```
{
  "code": 0,
  "message": "success",
  "data": [
    {
      "completedAt": 1544498434000,
      "createdAt": 1551792171000,
      "id": 3,
      "orderBy": "",
      "paperId": 1,
      "questions": "{\"1\":\"what is the simple method?\"}",
      "remark": "",
      "score": 100,
      "startedAt": 1544498434000,
      "studentAnswer": "{\"1\":\"A\",\"2\":\"B\"}",
      "studentId": 2,
      "teacherCorrection": "{\"1\":{\"correct\":true,\"score\":10,\"remark\":\"这道题应该这么解\"}}",
      "teacherId": 1,
      "updatedAt": 1551792171000
    }
  ]
}
 
```

### 2. 获取答卷详情
| 接口地址     | /api/pc/getAnswerSheet/{answerSheetId} |
| ------------ | ---------------------------------- |
| **请求方法** | GET                    |
| **详细说明** | 获取答卷详情 |



#### 接口响应
```
{
  "code": 0,
  "message": "success",
  "data": {
    "completedAt": 1544498434000,
    "createdAt": 1551792171000,
    "id": 3,
    "orderBy": "",
    "paperId": 1,
    "questions": "{\"1\":\"what is the simple method?\"}",
    "remark": "",
    "score": 100,
    "startedAt": 1544498434000,
    "studentAnswer": "{\"1\":\"A\",\"2\":\"B\"}",
    "studentId": 2,
    "teacherCorrection": "{\"1\":{\"correct\":true,\"score\":10,\"remark\":\"这道题应该这么解\"}}",
    "teacherId": 1,
    "updatedAt": 1551792171000
  }
}
 
```

### 3. 批改答卷
| 接口地址     | /api/pc/reviseAnswerSheet |
| ------------ | ---------------------------------- |
| **请求方法** | POST                    |
| **详细说明** | 批改答卷 |

#### body参数 --json
```
{
    "id":1, //答卷ID
    "score":95, //打分
    "teacherCorrection":{ //批改
        "1":{
            "correct":true,
            "score":10,
            "remark":"这道题应该这么解"
        }
       
    }      
}
```

#### 接口响应
```
{
  "code": 0,
  "message": "success",
}
 
```








#### 错误码
| 错误码 | 说明 |
| ------- | ------- |
|200001 | 参数不可为空 |
|200002 | 参数错误 |
|200003 | 账号不存在 |
|200004 | 账号不唯一 |
|200005 | 账号不可用 |
|200006 | 用户不存在 |
|200007 | 登录错误，请联系系统管理员 |
|200008 | 密码错误 |
|200009 | 两次密码不一致 |
|200010 | 数据不存在 |
|200011 | userToken为空 |
|200012 | token过期或userToken不正确 |
|200013 | credence错误 |
|200014 | 系统错误 |


