# transaction examples

#JDBCTransaction.java- simple JDBC local transaction example.
</br>
#hibernate basic transaction example with MYSQL DB



What is a transaction?
A transaction defines a logical unit of work that either completely succeeds or produces no result at all. There are two types of transactions - local and distributed transaction.

Why we need transaction management?
A database transaction, by definition, must be atomic. In order to maintain consistency in a database, before and after the transaction, few properties are followed. These are called ACID properties. 

Atomicity - It ensures that either all or none of the operations needs to be performed.

Consistency - It ensures that either all operations are rolled back and set to the state back from where the transaction was started or the changes were successfully made and reflected.

Isolation - It ensures that transactions performed are only reflected after a commit.

Durability - It ensures that the committed transactions are persisted.

What are Transactional databases?
A transactional database is a DBMS that provides the ACID properties. Not having ACID properties means that the database works well on clusters because they are horizontally scalable. NoSQL follows a model known as the BASE (Basically Available, Soft state, Eventual consistency).

Types of Transactions
Local Transaction:  Local transactions are those that affect only one transaction resource.
No alt text provided for this image
Local transaction example in JDBC API:
JDBC API is auto-commit by default. To enable the transaction, set auto-commit to false. transactions are managed at the connection level.

github link

Local transaction example in hibernate:- In hibernate a transaction is associated with a Session and is usually instantiated by a call to Session.beginTransaction(). A single session might span multiple transactions. However, it is intended that there be at most one uncommitted Transaction associated with a particular Session at any time.

github link

Local transaction example in spring:-
 Spring declarative transaction model uses AOP proxy. in spring by default propagation level is REQUIRED which means the inner transaction will be part of the same outer transaction, hence if the inner transaction fails the whole transaction will get rollback. it's important to know that Rollback works for only Runtime exceptions by default. For checked Exceptions, we have to specify that explicitly @Transcational(rollbackFor = Exception.class) Mixing the database I/O with other types of I/O like webservice call , in a transactional context is a code smell and may lead to connection exhaust.

github link

Note :- Spring supports distributed JTA transactions across multiple XA resources by using Transaction Manager like Atomikos, JBossTS or Bitronix etc. JTA transactions are also supported when deploying to a suitable Java EE Application Server. we will cover it in distributed transaction section.

Before getting into the distributed transactions details, let's understand JTA-

JTA (Java transaction API) :
JTA defines a high-level transaction management interface intended for resource managers and transactional applications in DTP (distributed transaction processing) environments.

It allows applications to perform distributed transactions, that is, transactions that access and update data on two or more networked computer resources. The JTA specifies standard Java interfaces between a transaction manager and the parties involved in a distributed transaction system.

1-UserTransaction—The javax.transaction.UserTransaction interface provides the application the ability to control transaction boundaries programmatically.

2—Transaction Manager—The javax.transaction.TransactionManager interface allows the application server to control transaction boundaries on behalf of the application being managed.

3—XAResource—The javax.transaction.xa.XAResource interface is a Java mapping of the industry standard XA interface based on the X/Open CAE Specification (Distributed Transaction Processing: The XA Specification).

XA Protocol :
The XA interface specifies communication between a transaction manager (TM) and a resource manager (RM). To communicate with the transactional resources, the transaction monitor must speak a common protocol with transactional resources. Usually, this protocol is a specification called XA.

Note :- Java EE application servers support JTA out of the box, and there are third party, standalone implementations of JTA that you can use to avoid being trapped on a Java EE application server.

2. Distributed Transaction:
Global transactions are those that span one or more transactional resources, and enlist them all in a single transaction. therefore it must be coordinated among those resources.

No alt text provided for this image
 There are two popular approaches for distributed transactions: 
Two Phase Commit Protocol and Eventual Consistency and Compensation also known as Saga pattern.

Problem :
In a monolithic system, we have a database system to ensure ACID properties. however, The microservice-based system does not have a global transaction coordinator by default. how do we manage transaction in micro service architecture?

Possible solutions:
1.Two-Phase Commit Protocol
This mechanism is designed initially for distributed systems. 2pc is widely used in database systems. As Microservices architecture inherently distributed systems in nature, we can use the Two-phase commit protocol (or 2PC) as one of the approaches. Primary drivers in a distributed transaction management are the message broker/transaction coordinator.

The distributed transaction contains two steps:

1.Prepare phase

Commit or Rollback phase
Prepare Phase:

All the participants of a transaction in this phase will be prepared for the commit and inform the transaction coordinator/message broker that they are ready for completing the transaction. it guarantee that the transaction is atomic.

Commit or Rollback phase:

In this phase, transaction coordinator will issue one of the commands they are a commit or a rollback to all the participants.

Benefits of using 2pc
2pc is a very strong consistency protocol and mainly used in DBMS system.
 2pc allows read-write isolation. This means the changes on a field are not visible until the coordinator commits the changes.
Disadvantages of using 2pc
1.The main issue with the 2PC approach is that it is a bit slow compared to the time for the operation on a single Microservice because it has to coordinate the transaction between services even if all the microservices are on the same network, still operation will be slow. So we need to be careful while implementing this for high demand services.

2. It is not really recommended for many microservice-based systems because 2pc is synchronous (blocking).

3.– Requires remote communication to each participating system.

4. Requires each participating system to support two-phase commit.

2. Eventual Consistency and Compensation (SAGA) :-
Eventual Consistency can be achieved by sagas. SAGA is one of the best way to ensure the consistency of the data in a distributed architecture without having a single ACID transaction. it maintains consistency (but not atomicity) across multiple services without requiring a transaction manager system.. A series of local transactions is SAGA. Saga pattern is an alternative to XA when resources are not XA capable or XA is undesired

Advantages of SAGA:-
1.Maintains consistency (but not atomicity) across multiple services without requiring a transaction manager system.

2.Uses a sequence of local TXs

3. Failure of a local TX executes a series of compensating transactions to undo the previous successful TXs

There are two types of sagas:
Choreography : each local transaction publishes domain events that trigger local transactions in other services.
Orchestration : an orchestrator (object) tells the participants what local transactions to execute.
1.Choreography-Based Saga
In this approach, there is no central orchestrator. Each service participating in the Saga performs their transaction and publish events. The other services act upon those events and perform their transactions. Also, they may or not publish other events based on the situation.

2.Orchestration-Based Saga
In this approach, there is a Saga orchestrator that manages all the transactions and directs the participant services to execute local transactions based on events. This orchestrator can also be though of as a Saga Manager.

