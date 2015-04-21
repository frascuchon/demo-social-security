# demo-social-security
Demo security integration with spring social 

## Configure

Define appId and appSecret for twitter provider connection in file `application.yml`
```
  social:
    twitter:
      appId: <SOME-TWITTER-APPID>
      appSecret: <SOME-TWITTER-APPSECRET>
```

## Run
```bash
mvn spring-boot:run 
```

You can access to `http://localhost:8080/`
