# demo2
通用mapper进行单表插入,查询  
redis实现短信验证码(注册,登录)  
redistemplate操作redis  
JWT生成token  
filter拦截用户资料请求  

### 流程


注册:  
用户在注册页面,输入完手机号和邮箱后发送  
checkEmall  检验邮箱是否重复  
checkPhoneNumber   检验手机号是否重复  
检验是否已经存在了邮箱和手机号,  
然后可以点击发送验证码,验证码有效期5分钟,需要离上次发送超过1分钟后才能再次发送验证码,  
 setVerifyCode  发送验证码到手机  
checkVerifyNumber  检查验证码是否正确  
如果验证码无误,保存用户信息,并返回token,有效期30分钟  

登录:  
输入手机号后检查是否存在  
checkPhoneNumber  
发送验证码,检查验证码正确  
调用login  
返回给用户token  


获取用户信息:  
过滤器拦截请求,检查token是否合法,合法允许访问,不合法拒绝访问  





=======================================================================
#### 所有接口均为post请求,数据格式均为json
 ## 接口


1  
描述: 获取用户信息,需要在请求头加一个authorization,值是token   
请求: http://localhost:8080/secure/getUserInfo  
参数 :  
在http headers中新增authorization
其值是类似于下面这种格式的token  
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJwaG9uZU51bWJlciI6IjExMjExMSIsIm5hbWUiOiJ6aGFuZ3NhbiIsImlkIjoiNCIsImV4cCI6MTU4NDI2Njg5OCwiaWF0IjoxNTg0MjY1MDk4fQ.97qfRDtkkHvFsRj_M1RBm-3PgrAliuN2zJAKf8xO2K8

返回值:User对象

<br>
2  

描述:生成验证码,有效期5分钟,生成1分钟后可以再次生成,验证码生成后会显示在控制台上  
请求:http://localhost:8080/setVerifyCode  
参数:  
{  
	"phoneNumber": "110"  
}  
 返回值:  
 set--设置成功  
 reset--重新设置验证码  
 unset--设置失败


3  
描述:检查邮箱是否存在于数据库中  
请求:http://localhost:8080/checkEmall  
参数:  
{  
    "emall": "114151977@qq.com"  
}  
返回值:存在 or 不存在  

4   
描述:检查手机号是否存在于数据库中  
请求:http://localhost:8080/checkPhoneNumber  
参数:  
{  
    "emall": "110111"  
}  
返回值:存在 or 不存在

5  
描述:登录,需要短信验证码  
请求:http://localhost:8080/login  
参数:  
{  
    "phoneNumber": "112111",  
    "verifyNumber": "389317"  
}  
注意verifyNumber需要调用发送验证码接口然后在控制台获取  
返回值:token

6  
描述:注册,调用获取验证码后使用  
http://localhost:8080/signUp  
参数:  
{  
    "name": "zhangsan",  
    "gender": "0",  
    "emall": "76977@qq.com",  
    "phoneNumber": "112111",  
    "verifyNumber":"325053"  
}  
验证码需在控制台获取
返回值:token  


