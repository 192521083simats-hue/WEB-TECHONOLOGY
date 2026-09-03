<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">

<html>
<head>
    <title>High Enrollment Courses</title>

    <style>
        body {
            font-family: Arial;
            text-align: center;
        }

        table {
            margin: auto;
            border-collapse: collapse;
            width: 80%;
        }

        th, td {
            border: 1px solid black;
            padding: 10px;
        }

        th {
            background-color: lightblue;
        }
    </style>
</head>

<body>

<h2>High Enrollment Courses</h2>

<table>

<tr>
    <th>Course ID</th>
    <th>Course Code</th>
    <th>Course Name</th>
    <th>Faculty</th>
    <th>Students</th>
    <th>Credits</th>
    <th>Type</th>
</tr>

<xsl:for-each select="courses/course[students &gt; 40]">

    <xsl:sort select="students"
              data-type="number"
              order="descending"/>

    <tr>
    <td>
    <xsl:value-of select="@id"/>
</td>
        <td>
            <xsl:value-of select="code"/>
        </td>

        <td>
            <xsl:value-of select="name"/>
        </td>

        <td>
            <xsl:value-of select="faculty"/>
        </td>

        <td>
            <xsl:value-of select="students"/>
        </td>

        <td>
            <xsl:value-of select="credits"/>
        </td>

        <td>
            <xsl:value-of select="type"/>
        </td>
    </tr>

</xsl:for-each>

</table>

</body>
</html>

</xsl:template>

</xsl:stylesheet>