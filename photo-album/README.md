Собирать с Java 11.  
Должен быть установлен maven (добавил на всякий wrapper, но не уверен, сработает ли он)  
Список команд для запуска

mvn clean package  
java -jar target/photo-album-0.1-spring-boot.jar

Префикс cloudlab я счел излишним, поэтому выполнять команды можно без написания cloudlab upload ...., а просто с помощью upload. если что реализована команда help, так что можно посмотреть как выполянть команды

Переменные среды.  
AWS-ACCESS-KEY-ID  
AWS_SECRET_ACCESS_KEY  
BUCKET 

Либо же можно при запуске приложения писать java -jar target/photo-album-0.1-spring-boot.jar --BUCKET=your_bucket --AWS-ACCESS-KEY-ID=your_key_id --AWS_SECRET_ACCESS_KEY=your_secret_key
