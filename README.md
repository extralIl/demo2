# demo2

通用mapper进行单表插入,查询
redis实现短信验证码(注册,登录)
JWT生成token
filter拦截用户资料请求


注册:
用户在注册页面,输入完手机号和邮箱后发送
checkEmall  检验邮箱是否重复
checkPhoneNumber   检验手机号是否重复
检验是否已经存在了邮箱和手机号,
然后可以点击发送验证码,验证码需要离上次发送超过1分钟后才能再次发送验证码,
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










