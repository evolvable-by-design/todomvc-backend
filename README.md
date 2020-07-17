# TODOMVC Backends - Evolution 1

## Evolution 1 - Change log and hints

- Type of evolution: Add or Remove Parameter
- Actual Evolution on the API: Add a required `dueDate` body parameter of type date (not date-time) to POST /todo
- Breaking Clients: Yes

**How to manage this evolution on the UI?**

Simply use the Windows prompt API to request the user to input this new information. Example: https://www.w3schools.com/jsref/met_win_prompt.asp

### How to start?

**Prerequisites:** you need to have a Java Development Kit (JDK) version 11 or later

```bash
cd backend
./gradlew bootRun
```

### How to use?

The default port of the server is 8080. This can be changed in the same manner as any Spring Boot project. Check Google for more information on that.

You can use the [OpenAPI documentation of the REST API](/openapi.yml) to discover how to use it. Its base URL is `http://localhost:8080/rest`.


## General Repository Information

This repository is a duplicate from [AntoineCheron/todomvc](https://github.com/AntoineCheron/todomvc).

Yet, this repository copies only the backend. The frontend has been deleted. Also, only the REST API is kept.

The code in this repository serve a research purpose. It is designed to lead an experiment with developers.

## How to install?

Open a terminal, go to the folder where you want to save this project and then type:

```sh
git clone https://github.com/evolvable-by-design/todomvc-backend.git
cd todomvc-backend
```

## License

The [license of this project](./LICENSE.txt) is MIT. It lets people do almost anything they want with this project, like making and distributing closed source versions.
