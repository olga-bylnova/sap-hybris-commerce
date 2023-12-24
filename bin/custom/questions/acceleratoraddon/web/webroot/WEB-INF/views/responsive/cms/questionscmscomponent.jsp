<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div style="color: red">here are the questions</div>
<div>
    <c:forEach var="question" items="${questions}">
        <span>Questions</span>
        <p>${question.questionCustomer}:</p>
        <span>${question.question}</span>
        <br/>
        <c:if test="${question.answerCustomer ne ''}">
            <span>Answers</span>
            <p>${question.answerCustomer}:</p>
            <span>${question.answer}</span>
        </c:if>
    </c:forEach>
</div>