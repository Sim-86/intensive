# 주제 - 팀과제 에서 추가로 프로모션 기능을 추가 하였습니다.

영화 예매 시스템에 프로모션 기능을 통해 할인 적용, 안내 등 기능 추가 하였습니다.

------


# 서비스 시나리오

## 기능적 요구사항

1. 사용자가 상품을 주문한다.(placeOrder)
2. 사용자는 상품주문을 취소 할 수 있다.(cancelOrder)
3. 결제 완료된다.
4. 결제 취소된다.
5. 배송 시작된다.
6. 배송 취소된다.

등등등



1. SAGA


PUT http://aca85c435d1a94d479d2c5ce91995bea-693040336.ap-northeast-2.elb.amazonaws.com:8080/payments/1

```json
{
    "deliveryStatus" : "cancelDelivery",
	"orderId" : 1
}
```

{"eventType":"DeliveryCanceled","timestamp":"20200903032522","id":null,"orderId":1,"deliveryStatus":"cancelDelivery","me":true}
{"eventType":"OrderCanceled","timestamp":"20200903032523","product":"컴퓨터","qty":2,"id":null,"me":true}

http://aca85c435d1a94d479d2c5ce91995bea-693040336.ap-northeast-2.elb.amazonaws.com:8080/orders/1
```json
{
    "product": "컴퓨터",
    "qty": 2,
    "orderStatus": "delivery canceled",
    "_links": {
        "self": {
            "href": "http://order:8080/orders/1"
        },
        "order": {
            "href": "http://order:8080/orders/1"
        }
    }
}
```


2. CQRS

CQRS는 bookingList로 구현

#첫번째 예시

Input
{"eventType":"Booked",
"timestamp":"20200902075717",
"customerId":1,
"quantity":3,
"price":2000.0,
"bookingStatus":"succeeded",
"seatId":2,
"bookingId":3,
"me":true}
output
{
   "_link":{
      "bookingList":{
        "href":"http://booking:8080/bookingLists/3"
      },
      "self":{
        "href":"http://booking:8080/bookingLists/3"
   }
  },
  "bookingId": 3,
  "bookingStatus": "succeeded",
  "customerId": 1,
  "notificationId": null,
  "notificationStatus": null,
  "paymentId": null,
  "paymentStatus": null, 
  "price": 2000.0, 
  "seatId": null,
  "seatStatus": null
}
#두번째 예시

Input
{"eventType":"PaymentSucceeded",
"timestamp":"20200902075717",
"paymentId":1431,
"paymentType":"credit card",
"bookingId":3,
"paymentStatus":"succeeded",
"seatId":2,
"me":true}
output
{
   "_link":{
      "bookingList":{
        "href":"http://booking:8080/bookingLists/3"
      },
      "self":{
        "href":"http://booking:8080/bookingLists/3"
   }
  },
  "bookingId": 3,
  "bookingStatus": "succeeded",
  "customerId": 1,
  "notificationId": null,
  "notificationStatus": null,
  "paymentId": 1431,
  "paymentStatus": succeeded, 
  "price": 2000.0, 
  "seatId": null,
  "seatStatus": null
}


3. Correlation

orderId 및 메시지 샘플

{"eventType":"SentSms","timestamp":"20200903113522","notificationId":24,"bookingId":0,"phoneNumber":"010-1234-5678","notificationStatus":"sent SMS promotionInserted","notificationType":null,"me":true}
{"eventType":"InsertedPromotion","timestamp":"20200903113532","promotionId":null,"discountRate":null,"paymentId":2,"note":null,"me":true}
{"eventType":"AppliedDiscount","timestamp":"20200903113532","promotionId":null,"discountRate":null,"paymentId":2,"note":null,"me":true}
{"eventType":"SentSms","timestamp":"20200903113532","notificationId":25,"bookingId":0,"phoneNumber":"010-1234-5678","notificationStatus":"sent SMS promotionInserted","notificationType":null,"me":true}
{"eventType":"InsertedPromotion","timestamp":"20200903114745","promotionId":null,"discountRate":null,"paymentId":1,"note":null,"me":true}
{"eventType":"AppliedDiscount","timestamp":"20200903114745","promotionId":null,"discountRate":null,"paymentId":1,"note":null,"me":true}
{"eventType":"SentSms","timestamp":"20200903114745","notificationId":26,"bookingId":0,"phoneNumber":"010-1234-5678","notificationStatus":"sent SMS promotionInserted","notificationType":null,"me":true}
{"eventType":"SelectedDiscount","timestamp":"20200903114745","paymentId":1,"bookingId":null,"totalPrice":null,"paymentStatus":"succeeded","paymentType":"card","seatId":null,"me":true}



4. REQ/RES

@FeignClient(name="promotion", url="${api.url.promotion}")
public interface PromotionService {

    @RequestMapping(method= RequestMethod.POST, path="/promotions")
    public long applyDiscount(@RequestBody Promotion promotion);

}
```

5. Gateway
앞단 spring cloud gateway 구성 
NAME                          TYPE           CLUSTER-IP       EXTERNAL-IP                                                                    PORT(S)          AGE
service/kubernetes            ClusterIP      10.100.0.1       <none>                                                                         443/TCP          18h
service/user18-gateway        LoadBalancer   10.100.233.106   a8e2d3e3632b049bb969cb018d93ba48-1627731876.ap-northeast-2.elb.amazonaws.com   8080:30104/TCP   8m26s
service/user18-notification   ClusterIP      10.100.140.162   <none>                                                                         8080/TCP         8m16s
service/user18-payment        ClusterIP      10.100.96.98     <none>                                                                         8080/TCP         8m20s
service/user18-promotion      ClusterIP      10.100.56.83     <none>                                                                         8080/TCP         8m23s

6. Deploy 
Good Good

7. Circuit Breaker


8. Autoscale(HPA)

NAME                                        REFERENCE          TARGETS   MINPODS   MAXPODS   REPLICAS   AGE
horizontalpodautoscaler.autoscaling/payment   Deployment/order   92%/5%    1         10        10         2m32s



9. readiness probe(zero downtime deployment), liveness probe


10. ConfigMap/Persistence

#configMap.yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: customer1
data:
  TEXT1 : MyConfigMap
  PaymentType : Card

sim86@DESKTOP-JSHVID3:~/final/gateway2/final-gateway$ kubectl get configmap
NAME        DATA   AGE
customer1   2      41s

```

11. Polyglot

################################################


