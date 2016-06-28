%
% ARCHIVO: eval.pl
% Evaluacion de BDL
% Maria Esther Vidal y Sandra Zabala
%

eval:- write('Seleccione el tipo de operador'),
       nl,
       write('1-Naive'),
       nl,
       write('2-Semi-naive con traza'),
       nl,
       write('3-Semi-naive sin traza'),
       nl,
       get(Op),
       write('Seleccione el programa a evaluar'),
       nl,
       write('1-Path'),
       nl,
       write('2-Transformacion Magica de Path'),
       nl,
       get(Prog), 
       ((Op = 49 -> consult("d:/Deutch/development/Rules4AMLIntegration/resources/naive.pl")); 
       ((Op = 50 -> consult("d:/Deutch/development/Rules4AMLIntegration/resources/semi.pl"));
                    consult("d:/Deutch/development/Rules4AMLIntegration/resources/semi1.pl"))),
       ((Prog = 49  -> consult("d:/Deutch/development/Rules4AMLIntegration/resources/ejemplo.pl")) ;
                       consult("d:/Deutch/development/Rules4AMLIntegration/resources/ej_mst.pl")),
       tdb.


























