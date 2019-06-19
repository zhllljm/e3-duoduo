<html>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<head>
    <title>student</title>
</head>
<body>
        学生信息:<br>
        学号：${student.id}&nbsp;&nbsp;&nbsp;
        姓名：${student.name}&nbsp;&nbsp;&nbsp;
        年龄：${student.age}&nbsp;&nbsp;&nbsp;
        家庭住址：${student.address}<br>
        学生列表：
        <table border="1">
            <tr>
                <td>序号</td>
                <th> 学号</th>
                <th>姓名</th>
                <th>年龄</th>
                <th>家庭住址</th>
            </tr>
            <#list studentList as student>
            <#if student_index % 2 ==0>
            <tr bgcolor="aqua">
            <#else>
             <tr bgcolor="#7fff00">
            </#if>
                <td>${student_index}</td>
                <td>${student.id}</td>
                <td>${student.name}</td>
                <td>${student.age}</td>
                <td>${student.address}</td>
            </tr>
            </#list>
        </table>

        <br>
        当前日期：${date?date}
        <br>
        当前时间：${date?time}
        <br>
        当前时间：${date?string("yyyy/MM/dd HH:mm:ss")}
        <br>
        引用模板测试：
        <#include  "hello.ftl">
</body>
</html>