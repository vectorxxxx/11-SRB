## 1、RabbitMQ

```shell
# 添加用户
rabbitmqctl.bat add_user srbuser srbuser

# 设置角色
rabbitmqctl.bat set_user_tags srbuser administrator

# 设置权限
rabbitmqctl.bat set_permissions -p / srbuser ".*" ".*" ".*"
```

