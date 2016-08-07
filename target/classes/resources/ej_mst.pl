<<<<<<< HEAD

%
% Archivo: ej_mst.pl
% Magic Sets Transformation de ejemplo.pl
% Maria Esther Vidal y Sandra Zabala
% Dic. 1994
%


:-dynamic(m_path/1).
:-dynamic(sup_path2_1/2).
:-dynamic(sup_path2_0/1).
:-dynamic(sup_path1_0/1).
:-dynamic(path/2).
:-dynamic(arc/2).


clause1(m_path(a),true).

clause1(m_path(Z),sup_path2_1(_,Z)).

clause1(sup_path1_0(X),m_path(X)).

clause1(path(X,Y),(sup_path1_0(X),arc(X,Y))).

clause1(sup_path2_0(X),m_path(X)).

clause1(sup_path2_1(X,Z),(sup_path2_0(X),arc(X,Z))).

clause1(path(X,Y),(sup_path2_1(X,Z),path(Z,Y))).



clause1(arc(a,b),true).
clause1(arc(h,a),true).
clause1(arc(a,c),true).
clause1(arc(c,c),true).
clause1(arc(c,d),true).
clause1(arc(b,f),true).
clause1(arc(f,g),true).











=======

%
% Archivo: ej_mst.pl
% Magic Sets Transformation de ejemplo.pl
% Maria Esther Vidal y Sandra Zabala
% Dic. 1994
%


:-dynamic(m_path/1).
:-dynamic(sup_path2_1/2).
:-dynamic(sup_path2_0/1).
:-dynamic(sup_path1_0/1).
:-dynamic(path/2).
:-dynamic(arc/2).


clause1(m_path(a),true).

clause1(m_path(Z),sup_path2_1(_,Z)).

clause1(sup_path1_0(X),m_path(X)).

clause1(path(X,Y),(sup_path1_0(X),arc(X,Y))).

clause1(sup_path2_0(X),m_path(X)).

clause1(sup_path2_1(X,Z),(sup_path2_0(X),arc(X,Z))).

clause1(path(X,Y),(sup_path2_1(X,Z),path(Z,Y))).



clause1(arc(a,b),true).
clause1(arc(h,a),true).
clause1(arc(a,c),true).
clause1(arc(c,c),true).
clause1(arc(c,d),true).
clause1(arc(b,f),true).
clause1(arc(f,g),true).











>>>>>>> 1e7055be8a216c6eb52108b2bbe671c1e738835b
