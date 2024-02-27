# Strawman for Kiota support of K8s CRD/CRs

This is a demo project to demonstrate the feasibility of using Kiota to generate client SDKs for Kubernetes [Custom Resource Definition](https://kubernetes.io/docs/tasks/extend-kubernetes/custom-resources/custom-resource-definitions/).

## Goals

Demonstrate that Kiota generated SDKs can be used to manipulate CRs in real Kubernetes cluster in, at least, 2 different programming languages (Java and Python)

## Non-Goals

- Cover the entire Kubernetes API
- Watch/Web Socket based operations

## Methodology

Kubernetes exposes [OpenAPI](https://github.com/kubernetes/kubernetes/tree/12217672a3511f5e461ecb87b3e22422ab30bdbf/api/openapi-spec) for all of the built-in resources (Pods, Deployments, ConfigMaps etc.) and for all of the CRDs installed.

It's easy to verify with a few steps:

- start a K8s cluster (I'm using `minikube`)
- start a Proxy to communicate with the API Server: `kubectl proxy --port=8080`
- export the OpenAPI specification: `curl localhost:8080/openapi/v2 > k8s-openapi.json`

Now let's install a demo CRD:

- `kubectl apply -f ./demo/crontab.yaml`
- export the OpenAPI specification: `curl localhost:8080/openapi/v2 > k8s-openapi-with-crontab.json`

I've done it for you and you can find the resulting files in the `./demo` folder.
To quickly browse the result take a look at `./demo/diff-definitions.yaml` and `diff-endpoint.yaml`.

Instead of having to rely on a live cluster to have the OpenApi description of the CRD endpoints we are going to write a little tool to convert from a `CustomResourceDefinition` yaml file to an OpenAPI specification.

Using the tool we will invoke pure `kiota` commands to generate the Python and Java SDKs.
We will build two demo programs to perform operations on the generated endpoints against a live cluster.
We are going to run those tests on a GH Action CI.

## Results

We should be able to use `kiota` as a viable client code generator for interacting with Kubernetes CRD/CRs.
