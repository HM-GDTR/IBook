# API文档

1. **用户API**
   - 用户包含的数据有
    ```json
    {
        uid(唯一,主键)
        用户名(唯一)
        密码哈希(不可见)
        密码盐(不可见)
        
        头像
        昵称
        个性签名
        生日
        性别
        注册日期
    }
    ```
    
   - `/api/user/alive` - 用户检测是否在线

    ```json
    {
        "参数样例": {
            "uuid": "登录令牌"
        },
        "返回样例": {
            "code": "状态代码",
            "reason": "给出状态代码的理由",
            
            "uid": "账号id"
        }
    }
    ```
    
   - `/api/user/signup` - 用户注册
   
    ```json
    {
        "参数样例": {
            "uname": "用户名",
            "passwd": "密码"
        },
        "返回样例": {
            "code": "状态代码",
            "reason": "给出状态代码的理由",
            
            "uid": "注册的账号id"
        }
    }
    ```

   - `/api/user/modifyPassWd` - 用户密码修改

    ```json
    {
        "参数样例": {
            "uuid": "登录令牌",
            "passwd": "新密码"
        },
        "返回样例": {
            "code": "状态代码",
            "reason": "给出状态代码的理由"
        }
    }
    ```

   - `/api/user/modify` - 用户修改

    ```json
    {
        "参数样例": {
            "uuid": "登录令牌",
            
            "avatar": "头像",
            "nickname": "昵称",
            "sign": "个性签名",
            "birthday": "生日",
            "gender": "性别"
        },
        "返回样例": {
            "code": "状态代码",
            "reason": "给出状态代码的理由",
        }
    }
    ```
   
   - `/api/user/find` - 用户查询
   
    ```json
    {
        "参数样例": {
            "uuid": "登录令牌",
            "uid": "uid"
        },
        "返回样例": {
            "code": "状态代码",
            "reason": "给出状态代码的理由",
            
            "uid": "uid",
            "avatar": "头像",
            "nickname": "昵称",
            "sign": "个性签名",
            "birthday": "生日",
            "gender": "性别",
            "reg_date": "注册日期"
        }
    }
    ```
   
   - `/api/user/login` - 用户登录
   
    ```json
    {
        "参数样例": {
            "uname": "用户名",
            "passwd": "密码"
        },
        "返回样例": {
            "code": "状态代码",
            "reason": "给出状态代码的理由",
            
            "uid": "账号id",
            "uuid": "登录令牌"
        }
    }
    ```
   
   - `/api/user/lodout` - 用户登出
   
    ```json
    {
        "参数样例": {
            "uuid": "登录id号"
        },
        "返回样例": {
            "code": "状态代码",
            "reason": "给出状态代码的理由"
        }
    }
    ```
   
2. **聊天API**

   - `/api/chat/send` - 发送消息

    ```json
    {
       "参数样例": {
           "uuid": "登录令牌",
           "target": "消息目的uid",
           "content": "消息内容"
       },
       "返回样例": {
           "code": "状态代码",
           "reason": "给出状态代码的理由"
       }
    }
    ```

   - `/api/chat/history` - 查询消息历史

    ```json
    {
        "参数样例": {
            "uuid": "登录令牌",
            "target": "消息的另一方, 可以为空或-1, 代表所有消息"
        },
        "返回样例": {
            "code": "状态代码",
            "reason": "给出状态代码的理由",
            "res": [
                {
                    "cid": "消息id",
                    "other": "另一人的uid",
                    "order": "消息的方向{1代表从uuid所在的uid发往to,-1则反之}",
                    "content": "消息内容"
                }
            ]
        }	
    }
    ```
   
3. **文章API**

   - `/api/article/release` - 文章发布

    ```json
    {
        "参数样例": {
            "uuid" : "登录令牌",
            "title": "标题",
            "content" : "文章内容"
        },
        "返回样例":
        {
            "code": "状态代码",
            "reason": "给出状态代码的理由"
        }
    }
    ```

   - `/api/article/show` - 文章查询

	```json
	{
		"参数样例": {
			"uuid": "登录令牌"
		},
		"返回样例": {
			"code": "状态代码",
			"reason": "给出状态代码的理由",
			"res": [
				{
					"aid": "文章id",
					"author": "作者uid",
					"title": "标题",
					"time": "发布时间",
					"content": "文章内容"
				}
			]
		}
	}
	```

   - `/api/article/modify` - 文章修改

	```json
	{
		"参数样例": {
			"aid": "文章id",
			"uuid": "登录令牌",
			"content": "修改后的文章内容"
		},
		"返回样例": {
			"code": "状态代码",
			"reason": "给出状态代码的理由"
		}
	}
	```

   - `/api/article/delete` - 文章删除

	```json
	{
		"参数样例": {
			"aid": "文章id",
			"uuid": "登录令牌"
		},
		"返回样例": {
			"code": "状态代码",
			"reason": "给出状态代码的理由"
		}
	}
	```

4. **评论API**

   - `/api/remark/release` - 评论发布

	```json
	{
		"参数样例": {
			"aid": "文章id",
			"uuid": "登录令牌",
			"content": "评论内容"
		},
		"返回样例": {
			"code": "状态代码",
			"reason": "给出状态代码的理由"
		}
	}
	```

   - `/api/remark/show` - 评论查询

	```json
	{
		"参数样例": {
			"aid": "文章id",
			"uuid": "登录令牌"
		},
		"返回样例": {
			"code": "状态代码",
			"reason": "给出状态代码的理由",
			"res": [
				{
					"rid": "评论id",
					"time": "发布时间",
					"author": "作者uid",
					"content": "评论内容"
				}
			]
		}
	}
	```

   - `/api/remark/modify` - 评论修改

	```json
	{
		"参数样例": {
			"rid": "评论id",
			"uuid": "登录令牌",
			"content": "更改后的评论内容"
		},
		"返回样例": {
			"code": "状态代码",
			"reason": "给出状态代码的理由"
		}
	}
	```

   - `/api/remark/delete` - 评论删除

	```json
	{
		"参数样例": {
			"rid": "评论id",
			"uuid": "登录令牌"
		},
		"返回样例": {
			"code": "状态代码",
			"reason": "给出状态代码的理由"
		}
	}
	```
