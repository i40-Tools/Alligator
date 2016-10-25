:- module( 'AML_facts_role_class', [] ).

amlPredicate(hasRoleClassLib/2).
amlPredicate(hasRoleClass/2).
amlPredicate(roleClassRefSem/2).
amlPredicate(classificationClass/2).
amlPredicate(eClassVersion/2).
amlPredicate(eClassIRDI/2).
amlPredicate(hasAttribute/2).
amlPredicate(hasRefSemantic/2).


:- discontiguous classificationClass/2 .
:- discontiguous eClassVersion/2.
:- discontiguous eClassIRDI/2.

 readFile(X,N):- 
	     open(X,read,Str),
         read(Str,N), 
         close(Str).


 readFile('resources/files/edb.txt',X),
 consult(X).


hasRoleClassLib(cAEXFile11,roleClassLib11).
hasInstanceHierarchy(cAEXFile11,instanceHierarchy11).
hasExternalReference(cAEXFile11,externalReference21).
hasExternalReference(cAEXFile11,externalReference11).
hasAdditionalInfomation(cAEXFile11,additionalInformation21).
hasAdditionalInfomation(cAEXFile11,additionalInformation11).
hasSchemaVersion(cAEXFile11,'2.15').
hasFileName(cAEXFile11,'seed-Granularity-0.aml').
type(cAEXFile11,cAEXFile).
hasRefSemantic(attribute41,refSemantic11).
hasAttributeName(attribute41,'Construction form of DC motor').
type(attribute41,attribute).
refBaseClassPath(externalReference11,'Libs/RoleClass Libraries/AutomationMLBaseRoleClassLib.aml').
externalReferenceAlias(externalReference11,'BaseRoleClassLib').
type(externalReference11,externalReference).
hasCorrespondingAttributePath(refSemantic31,'0173-1#02-AAO663#002').
type(refSemantic31,refSemantic).
hasAttributeValue(attribute31,'0173-1---BASIC_1_1#01-ABW077#009').
hasAttributeName(attribute31,'eClassIRDI').
type(attribute31,attribute).
hasAttributeName(internalElement11,'InstanceMyMotor').
identifier(internalElement11,'3fc3ade7-aa58-44a4-ab37-fa7e7665a74e').
type(internalElement11,internalElement).
hasCorrespondingAttributePath(refSemantic21,'ECLASS:0173-1#02-BAE122#006').
type(refSemantic21,refSemantic).
hasAttributeValue(attribute21,'27022501').
hasAttributeName(attribute21,'eClassClassificationClass').
type(attribute21,attribute).
hasCorrespondingAttributePath(refSemantic11,'ECLASS:0173-1#02-BAE069#007').
type(refSemantic11,refSemantic).
hasInternalElement(instanceHierarchy11,internalElement11).
hasAttributeName(instanceHierarchy11,'InstanceHierarchy1').
type(instanceHierarchy11,instanceHierarchy).
hasAttributeValue(attribute11,'9.0').
hasAttributeName(attribute11,'eClassVersion').
type(attribute11,attribute).
hasLastWritingDateTime(additionalInformation21,'2012-02-20 ').
hasWriterID(additionalInformation21,'AutomationML e.V.').
hasWriterRelease(additionalInformation21,'1.0').
hasWriterVendorURL(additionalInformation21,'www.AutomationML.org').
hasWriterName(additionalInformation21,'AutomationML e.V.').
type(additionalInformation21,additionalInformation).
hasWriterProjectTitle(additionalInformation21,'AutomationML Tutorial Examples').
hasWriterVersion(additionalInformation21,'1.0').
hasWriterProjectID(additionalInformation21,'AutomationML Tutorial Examples ').
hasWriterVendor(additionalInformation21,'AutomationML e.V.').
hasRefSemantic(attribute61,refSemantic31).
hasAttributeName(attribute61,'GTIN').
type(attribute61,attribute).
hasAttribute(roleClass11,attribute61).
hasAttribute(roleClass11,attribute51).
hasAttribute(roleClass11,attribute41).
hasAttribute(roleClass11,attribute31).
hasAttribute(roleClass11,attribute21).
hasAttribute(roleClass11,attribute11).
hasAttributeName(roleClass11,'BASIC_27-02-25-01 DC engine (IEC)').
type(roleClass11,roleClass).
hasAutomationMLVersion(additionalInformation11,'2.0').
type(additionalInformation11,additionalInformation).
hasRefSemantic(attribute51,refSemantic21).
hasAttributeName(attribute51,'Cooling type').
type(attribute51,attribute).
refBaseClassPath(externalReference21,'Libs/InterfaceClass Libraries/AutomationMLInterfaceClassLib.aml').
externalReferenceAlias(externalReference21,'BaseInterfaceClassLib').
type(externalReference21,externalReference).
hasRoleClass(roleClassLib11,roleClass11).
hasVersion(roleClassLib11,'1.0.0').
hasAttributeName(roleClassLib11,'ExampleEClassRoleClassLib').
type(roleClassLib11,roleClassLib).
refBaseClassPath(externalReference12,'Libs/RoleClass Libraries/AutomationMLBaseRoleClassLib.aml').
externalReferenceAlias(externalReference12,'BaseRoleClassLib').
type(externalReference12,externalReference).
hasAttributeValue(attribute32,'0173-1---BASIC_1_1#01-ABW077#009').
hasAttributeName(attribute32,'eClassIRDI').
type(attribute32,attribute).
hasInternalElement(instanceHierarchy12,internalElement12).
hasAttributeName(instanceHierarchy12,'InstanceHierarchy1').
type(instanceHierarchy12,instanceHierarchy).
hasAttributeValue(attribute12,'9.0').
hasAttributeName(attribute12,'eClassVersion').
type(attribute12,attribute).
hasRoleClassLib(cAEXFile12,roleClassLib12).
hasInstanceHierarchy(cAEXFile12,instanceHierarchy12).
hasExternalReference(cAEXFile12,externalReference22).
hasExternalReference(cAEXFile12,externalReference12).
hasAdditionalInfomation(cAEXFile12,additionalInformation22).
hasAdditionalInfomation(cAEXFile12,additionalInformation12).
hasSchemaVersion(cAEXFile12,'2.15').
hasFileName(cAEXFile12,'seed-Granularity-1.aml').
type(cAEXFile12,cAEXFile).
hasWriterProjectTitle(additionalInformation22,'AutomationML Tutorial Examples').
hasWriterRelease(additionalInformation22,'1.0').
hasWriterVendorURL(additionalInformation22,'www.AutomationML.org').
hasWriterID(additionalInformation22,'AutomationML e.V.').
type(additionalInformation22,additionalInformation).
hasRoleClass(roleClassLib12,roleClass12).
hasVersion(roleClassLib12,'1.0.0').
hasAttributeName(roleClassLib12,'ExampleEClassRoleClassLib').
type(roleClassLib12,roleClassLib).
refBaseClassPath(externalReference22,'Libs/InterfaceClass Libraries/AutomationMLInterfaceClassLib.aml').
externalReferenceAlias(externalReference22,'BaseInterfaceClassLib').
type(externalReference22,externalReference).
hasAttribute(roleClass12,attribute42).
hasAttribute(roleClass12,attribute32).
hasAttribute(roleClass12,attribute22).
hasAttribute(roleClass12,attribute12).
hasAttributeName(roleClass12,'BASIC_27-02-25-01 DC engine (IEC)').
type(roleClass12,roleClass).
hasAttributeName(internalElement12,'InstanceMyMotor').
identifier(internalElement12,'3fc3ade7-aa58-44a4-ab37-fa7e7665a74e').
type(internalElement12,internalElement).
hasRefSemantic(attribute42,refSemantic12).
hasAttributeName(attribute42,'Cooling type').
type(attribute42,attribute).
hasAttributeValue(attribute22,'27022501').
hasAttributeName(attribute22,'eClassClassificationClass').
type(attribute22,attribute).
hasCorrespondingAttributePath(refSemantic12,'ECLASS:0173-1#02-BAE122#006').
type(refSemantic12,refSemantic).
hasAutomationMLVersion(additionalInformation12,'2.0').
type(additionalInformation12,additionalInformation).

