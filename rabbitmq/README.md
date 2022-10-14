## Scenario

* 1.Spring AMQP를 이용해 Single Publisher 와 Consumer 로 메시지를 주고 받기
* 2.Spring AMQP를 이용해 Single Publisher 와 Multi Consumers 로 메시지를 주고 받기
* 3.Spring AMQP를 이용해 
  * 익명 큐를 이용해 Publish, Consume 하기
* 4.Spring AMQP를 이용해 Routing 하기
  * Fanout Exchange에 비해서 훨씬 빠르다
* 5.Spring AMQP를 이용해 Topic에 따라 메시지를 보내기
  * 한 쪽 큐에 메시지가 집중적으로 쌓이는 현상이 발생 -> 왜 그런걸까?
  * preset escape characters (* - 정확히 하나의 단어를 대치, # - 0개 이상의 단어를 대치) 에 따라 메시지 전송이 달라진다.
* Spring AMQP를 이용해 Publisher 가 메시지를 보냈음을 확인하기 -- Publisher Confirms
* Spring AMQP를 이용해 RPC 구현
  * 클라이언트가 요청을 큐에 보내면, 서버에서 메시지를 수신하고, 연산을 한 뒤 응답을 보내줌
* Spring AMQP를 이용해 여러 형태의 메시지를 보내고 받기 -- Converter
  * String Object,
  * JSON Object, 
      * Deserialization 문제를 어떻게 해결할까?,
      * 다른 POJO로 들어오는 메시지를 거르기 
  * Custom Object
* 8.Spring AMQP에서 익명 큐를 사용하는 경우, 컨슈머가 죽었을 때 어떻게 복구하는가.
  * 서버가 분리된 상태이다.
  * 처음 뜰 때 Anonymous, Exclusive Queue가 뜬다.
  * 컨슈머가 두 개 이상이다.
  * 컨슈머 중 한 개가 다운되었다.
  * 해당 큐를 살리면서 다른 컨슈머에 연결할 수 있는 방법?
* Spring AMQP에서 Exclusive하는 경우, 컨슈머가 죽었을 때 남아있는 메시지를 존속시키는 경우
* Spring AMQP를 이용해 ListenerContainer 로 메시지 받기
  * ListenerHandler
  * RabbitListener
  * ListenerContainer
  