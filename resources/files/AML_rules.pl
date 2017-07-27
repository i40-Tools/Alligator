%
% File: exampleAML.pl
%

:-dynamic(refSemantic/2).
:-dynamic(sameEClassSpec/2).
:-dynamic(hasRoleClassLib/2).
:-dynamic(hasRoleClass/2).
:-dynamic(roleClassRefSem/2).
:-dynamic(classificationClass/2).
:-dynamic(sameClassification/2).
:-dynamic(eClassVersion/2).
:-dynamic(eClassIRDI/2).
:-dynamic(sameEClassVersion/2).
:-dynamic(sameEClassIRDI/2).
:-dynamic(sameAttribute/2).
:-dynamic(sameRoleClass/2).
:-dynamic(sameRoleClassLib/2). 
:-dynamic(sameCAEXFile/2).
:-dynamic(hasAttribute/2).
:-dynamic(sameAttributeRoleClass/2).
:-dynamic(hasCorrespondingAttributePath/2).
:-dynamic(sameRefSemantic/2).
:-dynamic(hasRefSemantic/2).
:-dynamic(hasAttributeName/2).
:-dynamic(hasAttributeValue/2).
:-dynamic(type/2).
:-dynamic(eClassClassificationAtt/2).
:-dynamic(eClassVersionAtt/2).
:-dynamic(eClassIRDIAtt/2).
:-dynamic(sameInterfaceClass/2).
:-dynamic(type/2).
:-dynamic(sameEClassificationRoleClass/2).
:-dynamic(sameRoleClassLib/2).
:-dynamic(sameSystemUnitClass/2).
:-dynamic(sibling/2).
:-dynamic(concatString/2).
:-dynamic(identifier/2).
:-dynamic(sameIdentifier/2).
:-dynamic(sameId/2).
:-dynamic(hasInternalElement/2).
:-dynamic(hasInternalLink/2).
:-dynamic(hasRefPartnerSideA/2).
:-dynamic(hasRefPartnerSideB/2).
:-dynamic(diffAttribute/2).
:-dynamic(diffIdentifier/2).
:-dynamic(diffIdentifier2/2).
:-dynamic(diffInterfaceClass/2).
:-dynamic(diffRoleClass/2).
:-dynamic(diffRoleClassLib/2). 
:-dynamic(diffeClassClassificationAtt/2).
:-dynamic(diffeClassIRDI/2).
:-dynamic(diffEClassVersion/2).
:-dynamic(diffRoleClassLib/2).

% Finds substring
containsOnly(X,Y) :- forall(sub_atom(X,_,1,_,C), sub_atom(Y,_,1,_,C)).

% Attributes are the same if the have the same refSemantic 
clause1(sameAttribute(X,Y),( hasRefSemantic(X,T),hasRefSemantic(Y,Z),sameRefSemantic(T,Z))).
clause1(sameRefSemantic(X,Y),(hasCorrespondingAttributePath(X,Z),hasCorrespondingAttributePath(Y,Z))).
clause1(sameRefSemantic(X,Y),(sameRefSemantic(X,Z),sameRefSemantic(Z,Y))).

clause1(sameAttribute(X,Y),( hasAttributeName(X,Z),hasAttributeName(Y,Z))).

clause1(diffAttribute(X,Y),( hasAttributeName(X,Z),hasAttributeName(Y,R),not(Z=R))).

% not rule for above
clause1(diffAttribute(X,Y),( hasRefSemantic(X,T),hasRefSemantic(Y,Z),
hasCorrespondingAttributePath(T,W),
hasCorrespondingAttributePath(Z,R),not(W=R)
)).

% Internal Elements are the same if the have the same identifier 
clause1(sameIdentifier(X,Y),(identifier(X,T),identifier(Y,T))).

% not rule for above
clause1(diffIdentifier(X,Y),(identifier(X,Z),identifier(Y,T),not(Z=T))).


% Internal Elements are the same if the have the same InternalLink
clause1(sameIdentifier(X,Y),(hasInternalLink(X,T),hasInternalLink(Y,Z),
hasRefPartnerSideA(T,A),hasRefPartnerSideA(Z,A),
hasRefPartnerSideB(T,B),hasRefPartnerSideB(Z,B)
)).

% not rule for above
clause1(diffIdentifier2(X,Y),(hasInternalLink(X,T),hasInternalLink(Y,Z),
hasRefPartnerSideA(T,W),hasRefPartnerSideA(Z,P),
hasRefPartnerSideB(T,B),hasRefPartnerSideB(Z,D),
not(W=P),not(B=D)
)).


%clause1(sameIdentifier(X,Y),(hasAttributeName(X,'eClassClassificationClass'),
%                                     hasAttributeName(Y,'eClassClassificationClass'),
%                                     hasAttributeValue(X,Z),
%                                     hasAttributeValue(Y,Z))
%                                     ).




% Attributes are the same if the have the same refSemantic and if they contain -/_sybmbols
clause1(concatString(X,Y),( hasRefSemantic(X,T),hasRefSemantic(Y,Z),sameRefSemantic(T,Z),hasAttributeValue(X,A),hasAttributeValue(Y,B),(containsOnly('-',A);containsOnly('-',B);containsOnly('_',A);containsOnly('_',B);containsOnly('/',A);containsOnly('/',B)) )).
clause1(sameRefSemantic(X,Y),(hasCorrespondingAttributePath(X,Z),hasCorrespondingAttributePath(Y,Z))).
clause1(sameRefSemantic(X,Y),(sameRefSemantic(X,Z),sameRefSemantic(Z,Y))).



% Testing
clause1(sameAttributeRoleClass(Z,T),(sameAttribute(X,Y),hasAttribute(Z,X),hasAttribute(T,Y))).

% Attributes related to eClass
clause1(eClassClassificationAtt(X,Y),(hasAttributeName(X,'eClassClassificationClass'),
                                     hasAttributeName(Y,'eClassClassificationClass'),
                                     hasAttributeValue(X,Z),
                                     hasAttributeValue(Y,Z))
                                     ).
% Attributes related to eClass
clause1(diffeClassClassificationAtt(X,Y),(hasAttributeName(X,'eClassClassificationClass'),
                                     hasAttributeName(Y,'eClassClassificationClass'),
                                     hasAttributeValue(X,W),
                                     hasAttributeValue(Y,R),
                                     not(W=R)                               
                                     )).


                                     
clause1(eClassVersionAtt(X,Y),(hasAttributeName(X,'eClassVersion'),
                               hasAttributeName(Y,'eClassVersion'),
                               hasAttributeValue(X,Z),
                               hasAttributeValue(Y,Z),
                               sibling(X,T1),
                               sibling(Y,T2),
                               eClassIRDIAtt(T1,T2)
                               )).                                     


clause1(diffEClassVersion(X,Y),(hasAttributeName(X,'eClassVersion'),
                               hasAttributeName(Y,'eClassVersion'),
                               hasAttributeValue(X,W),
                               hasAttributeValue(Y,R),
                               not(W=R)                               
                               )).                                     





clause1(eClassIRDIAtt(X,Y),(  hasAttributeName(X,'eClassIRDI'),
                              hasAttributeName(Y,'eClassIRDI'),
                              hasAttributeValue(X,Z),
                              hasAttributeValue(Y,Z))
                              ).                                     


clause1(diffeClassIRDI(X,Y),(hasAttributeName(X,'eClassIRDI'),
                              hasAttributeName(Y,'eClassIRDI'),
                              hasAttributeValue(X,W),
                              hasAttributeValue(Y,R),
                               not(W=R))).                                     


clause1(sameRoleClass(Z,T),(  
                             type(Z,roleClass),
                             type(T,roleClass),
                             eClassClassificationAtt(X,Y),
                             eClassVersionAtt(B,C),
                             eClassIRDIAtt(D,E),
                             hasAttribute(Z,X),
                             hasAttribute(Z,B),
                             hasAttribute(Z,D),
                             hasAttribute(T,Y),
                             hasAttribute(T,C),
                             hasAttribute(T,E)
                             )).


clause1(diffRoleClass(Z,T),(  
                             type(Z,roleClass),
                             type(T,roleClass),
                             diffeClassClassificationAtt(X,Y),
                             diffeClassIRDI(B,C),
                             diffEClassVersion(D,E),
                             hasAttribute(Z,X),
                             hasAttribute(Z,B),
                             hasAttribute(Z,D),
                             hasAttribute(T,Y),
                             hasAttribute(T,C),
                             hasAttribute(T,E) 
                             )).

clause1(diffRoleClass(Z,T),(  
                             type(Z,roleClass),
                             type(T,roleClass),
                             diffeClassClassificationAtt(X,Y),
                             hasAttribute(Z,X),
                             hasAttribute(T,Y)
                             )).

clause1(diffRoleClass(Z,T),(  
                             type(Z,roleClass),
                             type(T,roleClass),
                             diffeClassIRDI(B,C),
                             hasAttribute(Z,B),
                             hasAttribute(T,C)
                             )).
clause1(diffRoleClass(Z,T),(  
                             type(Z,roleClass),
                             type(T,roleClass),
                             diffEClassVersion(D,E),
                             hasAttribute(Z,D),
                             hasAttribute(T,E) 
                             )).



                             
clause1(sameEClassificationRoleClass(Z,T),(  
                             type(Z,roleClass),
                             type(T,roleClass),
                             hasAttributeName(Z,'eClassClassSpecification'),
                             hasAttributeName(T,'eClassClassSpecification')
                             )).                             
                             
%clause1(sameInterfaceClass(Z,T),(
%                             type(Z,interfaceClass),
%                             type(T,interfaceClass), 
%                             eClassClassificationAtt(X,Y),
%                             eClassVersionAtt(B,C),
%                             eClassIRDIAtt(D,E),
%                             hasAttribute(Z,X),
%                             hasAttribute(Z,B),
%                             hasAttribute(Z,D),
%                             hasAttribute(T,Y),
%                             hasAttribute(T,C),
%                             hasAttribute(T,E)
%                             )).                             

%clause1(sameRoleClassLib(X,Y),(
%                             sameEClassificationRoleClass(Z,T),
%                            hasRoleClass(X,Z),
%                             hasRoleClass(Y,T)
%                             )). 
 
clause1(sameRoleClassLib(X,Y),(
                             type(X,roleClassLib),
                             type(Y,roleClassLib), 
                            hasAttributeName(X,T),
                            hasAttributeName(Y,T)
                             )). 
clause1(diffRoleClassLib(X,Y),(
                             type(X,roleClassLib),
                             type(Y,roleClassLib), 
                            hasAttributeName(X,W),
                            hasAttributeName(Y,R),
                            not(W=R)
                             )). 

clause1(sameRoleClass(X,Y),(
                             type(X,roleClass),
                             type(Y,roleClass), 
                            hasAttributeName(X,T),
                            hasAttributeName(Y,T)
                             )). 
clause1(diffRoleClass(X,Y),(
                             type(X,roleClass),
                             type(Y,roleClass), 
                            hasAttributeName(X,W),
                            hasAttributeName(Y,R),
                            not(W=R)
                             )). 



clause1(sameSystemUnitClass(X,Y),(
                             type(X,systemUnitClass),
                             type(Y,systemUnitClass), 
                            hasAttributeName(X,T),
                            hasAttributeName(Y,T)
                             )). 
clause1(diffSystemUnitClass(X,Y),(
                             type(X,systemUnitClass),
                             type(Y,systemUnitClass), 
                             hasAttributeName(X,W),
                             hasAttributeName(Y,R),
                             not(W=R)
                             )). 
  
clause1(sameSystemUnitClassLib(X,Y),(
                             type(X,systemUnitClassLib),
                             type(Y,systemUnitClassLib), 
                            hasAttributeName(X,T),
                            hasAttributeName(Y,T)
                             )). 
clause1(diffSystemUnitClassLib(X,Y),(
                             type(X,systemUnitClassLib),
                             type(Y,systemUnitClassLib), 
                             hasAttributeName(X,W),
                             hasAttributeName(Y,R),
                             not(W=R)
                             )). 

clause1(sameInterfaceClass(X,Y),(
                             type(X,interfaceClass),
                             type(Y,interfaceClass), 
                            hasAttributeName(X,T),
                            hasAttributeName(Y,T)
                             )).                             

clause1(diffInterfaceClass(X,Y),(
                             type(X,interfaceClass),
                             type(Y,interfaceClass), 
                             hasAttributeName(X,W),
                             hasAttributeName(Y,R),
                             not(W=R)
                             )). 


clause1(sameInterfaceClassLib(X,Y),(
                             type(X,interfaceClassLib),
                             type(Y,interfaceClassLib), 
                            hasAttributeName(X,T),
                            hasAttributeName(Y,T)
                             )).                             

clause1(diffInterfaceClassLib(X,Y),(
                             type(X,interfaceClassLib),
                             type(Y,interfaceClassLib), 
                             hasAttributeName(X,W),
                             hasAttributeName(Y,R),
                             not(W=R)
                             )). 


clause1(sameInstanceHierarichy(X,Y),(
                             type(X,instanceHierarchy),
                             type(Y,instanceHierarchy), 
                            hasAttributeName(X,T),
                            hasAttributeName(Y,T)
                             )).                             

clause1(diffInstanceHierarichy(X,Y),(
                             type(X,instanceHierarchy),
                             type(Y,instanceHierarchy), 
                             hasAttributeName(X,W),
                             hasAttributeName(Y,R),
                             not(W=R)
                             )). 
 
                             
%clause1(sameSystemUnitClass(Z,T),(
%                             type(Z,systemUnitClass),
%                             type(T,systemUnitClass), 
%                             eClassClassificationAtt(X,Y),
%                             eClassVersionAtt(B,C),
%                             eClassIRDIAtt(D,E),
%                             hasAttribute(Z,X),
%                             hasAttribute(Z,B),
%                             hasAttribute(Z,D),
%                             hasAttribute(T,Y),
%                             hasAttribute(T,C),
%                             hasAttribute(T,E)
%                             )).  



clause1(sibling(X,Y),(hasAttribute(Z,X),hasAttribute(Z,Y))).
