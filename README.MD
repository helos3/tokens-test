# Requests example
Tested with Postman

## POST /register

request
```$xslt
{
	"login": "user1",
	"password": "user"
}
```

response

```$xslt
code: 200
{
  "token": "iVtDdF3v8BBjguSfm0lHFteCgqgriVnCfeNmKF7jkhat5a25jYw1W46swBRuWf9XjEuJWD81/8r9+aGQXuYtZQ=="
}
```


## POST /token

request
```$xslt
{
	"login": "user1",
	"password": "user"
}
```

response

```$xslt
code: 200
{
  "token": "iVtDdF3v8BBjguSfm0lHFteCgqgriVnCfeNmKF7jkhat5a25jYw1W46swBRuWf9XjEuJWD81/8r9+aGQXuYtZQ=="
}
```

## POST /token

request
```$xslt
{
  "token": "iVtDdF3v8BBjguSfm0lHFteCgqgriVnCfeNmKF7jkhat5a25jYw1W46swBRuWf9XjEuJWD81/8r9+aGQXuYtZQ=="
}
```

response

```$xslt
code: 200
{
  "login": "user1"
}
```
