esta es la documentacion de mi primer historia de usuario en la semana 1 de mi ruta avanzada java , en la cual me pedian desarrolar una 
interfaz que me permitiera ver un empleado sus detalles e  informacion y ver a que compañia pertenecia 
- esta interfaz fue desarrollada por dos versiones de java que fueron la version java.8 y java.17 , se realizo de esta manera
 para poder saber unificar de manera correcta varias versiones de java sin problemas de compilacion , se es conciente que me falto
 dar una mejor estructuracion en en el proyecto , esto se tiene pendiente a mejorar en proximas semanas 

- Se noto unas grandes diferencias entre estas versiones que me gustaria documentar : Documentacion-
 en la versiona JAVA8 se envidencio que para representar datos se necesita defenir de manera muy explicita como queremos hacer 
 la representacion de dichos datos , requeremos declarar campos , hacer un constructor , getter y demas implementaciones ademas
 de hacerlo todo manualmente asignando dichos campos con .this y capturando en una varible 

- En Java17 se noto una gran diferencia que el metodo de representar datos tuvo una gran autonomia lo que nos costaba un gran trabajo
 repetitivo ahora se reduce a una linea , se declara un record y a este le pasaremos los campos que vamos a capturar como parametro ,
 pero estos campos seran inmutables a menos de que creemos una copia y elimines la original 

se evidencio que aunque la automatizacion nos ayuda mucho con la autonomia si neceistas hacer una estructuracion robusta y  manejar herencia o crear datos que podran cambiar mediante formulas o a lo largo de tiempo es mejor crear las clases e implementar la logica que nos otorga la versioan de java 8 pero si tu neceistas hacer una estructura que la neceisdad es tener datos fijos que se puedan mover de una punto A a un punto B deberias de implementar la logica de creacion de record 

hn