
FYI, this is a work in progress (wip) branch, and only works with Cascading wip-1.2.

Welcome

 This is the Cascading.Work module.

 It provides a simple framework for creating complex Cascading applications that work with
 data that must be accessed via multiple data format across multiple systems.

 See the SimpleTest test case for simple usage patterns.

 Cascading is a feature rich API for defining and executing complex,
 scale-free, and fault tolerant data processing workflows on a Hadoop
 cluster. It can be found at the following location:

   http://www.cascading.org/

Building

 This release requires at least Cascading 1.2.x. Hadoop 0.19.x or later,
 and a cluster that supports the memcached text or binary protocols.

 To build a jar,

 > ant -Dcascading.home=... -Dhadoop.home=... jar

 To test (requires a localhost memcached cluster),

 > ant -Dcascading.home=... -Dhadoop.home=... test

where "..." is the install path of each of the dependencies.

Using

  The cascading-work.jar file should be added to the "lib"
  directory of your Hadoop application jar file along with all
  Cascading dependencies.
