:- multifile(clause1/2).
 eval:- 
 assert(amlPredicates(
 [
 'sameRoleClassLib','sameRoleClass','sameInterfaceClass','sameSystemUnitClass',
 'eClassClassificationAtt','eClassVersionAtt','eClassIRDIAtt','sameAttribute' 
 ] 
 )),    
 consult('C:/Users/omar/Desktop/Alligator/resources/files/semi1.pl'),
 consult('C:/Users/omar/Desktop/Alligator/resources/files/AML_rules.pl'),
 consult('C:/HeterogeneityExampleData/AutomationML/M2-Granularity/Testbeds-2/edb.pl'),
 %consult('d:/Deutch/development/Rules4AMLIntegration/resources/files/TestData.pl'),
 tdb.       
                  
 writePredicates:-
 open('C:/output.txt',write,FileName),
 consult("C:/Users/omar/Desktop/Alligator/resources/files/writeRules.pl"),
 myWrite(FileName),close(FileName).             
                                                                             