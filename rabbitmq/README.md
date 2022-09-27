## Scenario

* Spring AMQP를 이용해 Single Publisher 와 Consumer 로 메시지를 주고 받기
* Spring AMQP를 이용해 Single Publisher 와 Multi Consumers 로 메시지를 주고 받기
* Spring AMQP를 이용해 
  * 익명 큐를 이용해 Publish, Consume 하기
* Spring AMQP를 이용해 Routing 하기
  * Fanout Exchange에 비해서 훨씬 빠르다
* Spring AMQP를 이용해 Topic에 따라 메시지를 보내기
  * 한 쪽 큐에 메시지가 집중적으로 쌓이는 현상이 발생 -> 왜 그런걸까?
  * preset escape characters (* - 정확히 하나의 단어를 대치, # - 0개 이상의 단어를 대치) 에 따라 메시지 전송이 달라진다.