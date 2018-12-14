/*
Edward R. Gonzalez 499406

Program will print out six predefined courses using
the provided instructions in the lab 1 pdf.

Affirmation of originality:
I affirm that I wrote this 
program myself without any help form any
other people or sources from the Internet.
*/

#include <stdio.h>
#include <stdlib.h>

typedef unsigned short uShortInt;


//Holds the information of a single course.
typedef struct CourseStruct
{
    char *Name[7];   //All courses have a 7 character representation.

    int Credits;

    float Grade;
} Course;

//Populates courses array with six different courses hard coded in their values.
void populateCoursesSix(Course *_courses, uShortInt _numCourses)
{
    //COP2210
    *_courses[0].Name    = "COP2210";
     _courses[0].Credits =         3;
     _courses[0].Grade   =     4.00f;

    //ENC1101
    *_courses[1].Name    = "ENC1101";
     _courses[1].Credits =         3;
     _courses[1].Grade   =     2.67f;

    //CGS3095
    *_courses[2].Name    = "CGS3095";
     _courses[2].Credits =         3;
     _courses[2].Grade   =     3.00f;

    //MAC
    *_courses[3].Name    = "MAC2311";
     _courses[3].Credits =         4;
     _courses[3].Grade   =     2.33f;

    *_courses[4].Name    = "COP3337";
     _courses[4].Credits =         3;
     _courses[4].Grade   =     4.00f;

    *_courses[5].Name    = "CDA3103";
     _courses[5].Credits =         3;
     _courses[5].Grade   =     3.00f;
}

//Prints the course information in the required format.
void printCourseInformation(Course *_courses, uShortInt _numCourses)
{
    char *TitleCourses           = "Courses"          ;
    char *TitleCredits           = "Credits"          ;
    char *TitleGrade             = "Grade"            ;
    char *TitleGradePointsEarned = "GradePointsEarned";

    char *FooterTotal             = "Total";
    int   CreditTotal             =       0;
    float GradesPointsEarnedTotal =       0;

    printf("%-10s", TitleCourses          );
    printf("%3s"  , TitleCredits          );
    printf("%7s"  , TitleGrade            );
    printf("%20s" , TitleGradePointsEarned);

    printf("\n\n");

    for (uShortInt index = 0U; index < _numCourses; index++)
    {
        printf("%-10s" , *_courses[index].Name                           );
        printf("%4d"   ,  _courses[index].Credits                        );
        printf("%10.2f",  _courses[index].Grade                          );
        printf("%20.2f",  _courses[index].Grade * _courses[index].Credits);
        printf("\n");

        CreditTotal             += _courses[index].Credits                        ;
        GradesPointsEarnedTotal += _courses[index].Grade * _courses[index].Credits;
    }

    printf("\n");

    printf("%-10s", FooterTotal);
    printf("%4d"  , CreditTotal);

    printf("          ");

    printf("%20.2f", GradesPointsEarnedTotal);

    printf("\n");

    printf("Current GPA: %0.2f", GradesPointsEarnedTotal / CreditTotal);
}

int main()
{
    uShortInt numCourses = 6;   //Size of courses array.

    Course *courses = (Course *)malloc(sizeof(Course) * numCourses);   //Storage array for all the courses initialized to the size of course times 6. 

    populateCoursesSix(courses, numCourses);   

    printCourseInformation(courses, numCourses);

    free(courses);

    return 0;
}