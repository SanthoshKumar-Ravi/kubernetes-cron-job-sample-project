# kubernetes-cron-job-sample-project
This is project is designed on how to avoid schedulers and start leveraging the kubernetes to manage the cronjobs. 

### The bottle neck of using spring schedulers are:

**Limited Concurrency Control:** Spring's default scheduling mechanisms, such as @Scheduled annotations, lack advanced concurrency control options. This can lead to problems when trying to manage concurrent execution of scheduled tasks.

**Potential Task Overlapping:** If a scheduled task takes longer to execute than the scheduling interval, it may overlap with subsequent executions, leading to unexpected behavior or resource contention.

**No Built-in Distributed Scheduling:** Spring's built-in scheduling features are typically designed for single-node applications. Handling distributed scheduling across multiple instances or microservices can be complex and may require additional solutions.

**Error Handling:** Spring's schedulers may not provide robust error handling and recovery mechanisms for tasks that fail during execution.

**Resource Consumption:** Poorly designed or long-running scheduled tasks can consume excessive resources, impacting the overall system's performance.

**Logging and Monitoring:** Effective monitoring and logging of scheduled tasks may require custom implementation, as Spring's built-in capabilities may not cover all use cases.

**Dependency Injection:** Dependency injection can be challenging in scheduled tasks, especially when dealing with asynchronous or parallel execution. Managing dependencies correctly can be error-prone.

**Complex Scheduling Logic:** Spring's scheduling capabilities may not handle complex scheduling scenarios, such as dynamic or event-driven scheduling requirements.

**Testing:** Unit testing scheduled tasks can be challenging, as you need to ensure that tasks execute correctly and on time during testing.

**Scalability:** Ensuring that scheduled tasks scale seamlessly as the application grows or as more instances are added can be a complex task.

ShedLock and Quartz are primarily focused on scheduling tasks and managing concurrency within a single application or distributed setup, while Kubernetes CronJobs are tailored for containerized environments and are part of Kubernetes' orchestration capabilities

## About Project
This is very simple project developed using Java + Maven.

## Deployment Steps:
### Download application
``` git clone https://github.com/SanthoshKumar-Ravi/kubernetes-cron-job-sample-project.git ```

### Go the folder
``` kubernetes-cron-job-sample-project ```

### Build the application
``` mvn clean package ```

### Build and push the image
```
docker build -t krpsanthoshkumar/sample-project-0.0.1 .
docker push krpsanthoshkumar/sample-project-0.0.1
```

### Apply cron yaml:
``` kubectl apply -f fetch_latest_payment_cron.yaml ```

### To List the running cronjobs:
``` kubectl get cronjobs ```

### To List the running jobs:
``` kubectl get jobs ```

### To List the pods:
``` kubectl get pods ```

## Yaml Breakdown:
```
apiVersion: batch/v1
kind: CronJob
metadata:
  name: sample-job
spec:
  schedule: "* * * * *"
  successfulJobsHistoryLimit: 1
  failedJobsHistoryLimit: 5
  jobTemplate:
    spec:
      parallelism : 2
      template:
        spec:
          containers:
            - name: sample
              image: {image_name}
              imagePullPolicy: Always
          restartPolicy: OnFailure
          terminationGracePeriodSeconds: 30
```


|                            Parameters                            | Description |
|:----------------------------------------------------------------:|:-----------:|
|                          spec.schedule                           |    A mandatory field that defines the schedule for the cron job using the standard cron syntax.     |
|                 spec.successfulJobsHistoryLimit                  |     An optional field that allows you to determine the number of successful executions to record in the history. The default value is 3.     | 
|                   spec.failedJobsHistoryLimit                    |    An optional field that defines the number of unsuccessful executions to retain in the history. The default value is set to 1.     |
|                spec.jobtemplate.spec.parallelism                 |    Specifies the number of job instances that can run concurrently.     |
|       spec.jobtemplate.spec.template.spec.containers.name        |    Assigns a name to the container.     |
|       spec.jobtemplate.spec.template.spec.containers.image       |    Specifies the Docker image to use for the container     |
|                spec.jobtemplate.spec.parallelism                 |    Determines when to pull the specified image     |
|                spec.jobtemplate.spec.parallelism                 |    Specifies the restart policy for the pod     |

