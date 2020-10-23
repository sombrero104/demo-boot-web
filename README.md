# 스프링부트 스프링MVC 설정
<br/>

## Formatter
<img src="./images/formatter.png" width="70%"><br/>
- Parser: 어떤 문자열을 (Locate 정보를 참고하여) 객체로 어떻게 변환할 것인가.
- Printer: 해당 객체를 (Locale 정보를 참고하) 문자열로 어떻게 출력할 것인가.

## Formatter vs Converter
Convertet는 Formatter보다 좀 더 General한 용도로 쓰임.<br/>
자바 객체를 다른 자바 객체로 변환한다던가..<br/>
하지만 주로 문자열을 객체로 변환한다던가 객체를 문자열로 변환하는 것을 많이 사용하기 때문에<br/>
Converter보다는 Formatter를 주로 많이 사용. <br/>
<br/><br/>

## 스프링 데이터 JPA
#### 도메인 클래스 컨버터
- 스프링 데이터 JPA가 제공하는 Repository를 사용해서 ID에 해당하는 엔티티를 읽어온다. <br/>
    (ID를 기준으로 ID에 해당하는 도메인 클래스로 컨버팅해준다.)<br/>
    (컨버팅을 Repository에서 하기 때문에 Repository가 있어야함.)<br/>
<br/><br/>

## HandlerInterceptor
- 핸들러 맵핑에 설정할 수 있는 인터셉터.<br/>
- 핸들러를 실행하기 전, 후(아직 렌더링 전) 그리고 완료(렌더링까지 끝난 이후) <br/>
    시점에 부가 작업을 하고 싶은 경우에 사용할 수 있다.<br/>
- preHandle -> 요청 처리 -> postHandle -> 뷰 렌더링 -> afterCompletion <br/>
- preHandle(1) -> preHandler(2) -> 요청 처리 -> postHandle(2) -> postHandle(1)  <br/>
    -> 뷰 렌더링 -> afterCompletion(2) -> afterCompletion(1) <br/>
- @RestController 같은 경우에는 렌더링할 뷰가 없기 때문에 postHandle 다음에 afterCompletion이 바로 실행된다. <br/>
- 비동기에서는 postHandle과 afterCompletion이 호출되지 않는다. <br/>
    대신, AsyncHandlerInterceptor가 제공하는 다른 메소드가 호출된다. <br/>
- 여러개의 인터셉터를 사용할 경우 order로 순서를 설정할 수 있다. <br/>
- 여러 핸들러에서 반복적으로 사용하는 코드를 줄이고 싶을 때 사용할 수 있다.<br/>
    - 로깅, 인증 체크, Locale 변경 등.<br/>

#### boolean preHandle(request, response, handler)
- 핸들러 실행하기 전에 호출 됨.
- '핸들러'에 대한 정보를 사용할 수 있기 때문에 서블릿 필터에 비해 보다 세밀한 로직을 구현할 수 있다.
- 리턴값으로 계속 다음 인터셉터 또는 핸들러로 요청, 응답을 전달할지(true), 응답 처리가 이곳에서 끝났는지(false) 알린다.

#### void postHandle(request, response, modelAndView)
- 핸들러 실행이 끝나고 아직 뷰를 렌더링 하기 이전에 호출됨.
- modelAndView를 전달받기 때문에 modelAndView에 추가적인 작업을 할 수 있다. 
- '뷰'에 전달할 추가적이거나 여러 핸들러에서 공통적인 모델 정보를 담는데 사용할 수도 있다.
- 이 메소드는 인터셉터 역순으로 호출된다.
- 비동기적인 요청 처리 시에는 호출되지 않는다.

#### void afterCompletion(request, response, handler, ex)
- 요청 처리가 완전히 끝난 뒤(뷰 렌더링 끝난 뒤)에 호출 됨.
- preHandler에서 true를 리턴한 경우에만 호출 됨.
- 이 메소드는 인터셉터 역순으로 호출된다.
- 비동기적인 요청 처리 시에는 호출되지 않는다. 

#### 인터셉터 vs 서블릿 필터
- 인터셉터는 서블릿 보다 구체적인 처리가 가능하다.
- 스프링MVC와 관련이 있을 때에 인터셉터 사용.<br/>
- 서블릿은 보다 일반적인 용도의 기능을 구현하는데 사용하는게 좋다.
    - 예를 들어, <br/>
    XSS(Cross Site Scripting, 입력 폼에 스크립트를 넣어서 클라이언트 정보를 빼냄.)를 차단하는 경우,<br/>
    스프링MVC와는 관련이 없으므로 서블릿 필터 사용. <br/>

<br/><br/>
