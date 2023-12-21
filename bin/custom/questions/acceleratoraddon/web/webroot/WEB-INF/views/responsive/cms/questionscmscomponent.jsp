<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div style="color: red">here are the questions</div>
<div>
    <c:forEach var="question" items="${questions}">
        <p>Question:
            <span>${question.question}</span>
        </p>
        <c:if test="${question.answerCustomer ne ''}">
            <p>Answer:
                <span>${question.answer}</span>
            </p>
        </c:if>
    </c:forEach>
</div>