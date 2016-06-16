#BuildItBigger
1.Project contains a Java library named `javajokes` for supplying jokes        
2.Project contains an Android library named `displayjokes` with an activity that displays jokes passed to it as intent extras.
3.Project contains a Google Cloud Endpoints module named `backend` that supplies jokes from the Java library. Project loads jokes from GCE module via an async task.            
4.Project contains connected tests to verify that the async task is indeed loading jokes.
Functional tests for both he flavors are stored in their respective folders named `androidTestFree` and `androidTestPaid`     
5.Project contains paid/free flavors. The paid flavor has no ads, and no unnecessary dependencies.     



#Additional Features:
1.Interstitial ad is shown between the main activity and the joke-displaying activity in `free flavor`.                       
2.App displays a loading indicator while the joke is being fetched from the server.                 
3.Gradle Task with name `theTask` does the following :          
starts GCE Server -> Runs Connected Android Tests - > Stops GCE Server
