# TODOs Manager API Autotests

## Getting Started

1. **Git clone the repo**
2. **Install [Docker Desktop](https://www.docker.com/products/docker-desktop/)**
3. **Run Docker Engine** (runs automatically when Docker Desktop opens)

## Test Execution

1. **Run Gradle Test:**
    1. For Unix systems (**Linux/MacOS**): <br>
        ```bash
        ./gradlew cleanTest test
        ```
    2. For **Windows**: <br>
        ```bash
        ./gradlew.bat cleanTest test
        ```

## Test Cases

### `GET /todos`

* [ ] **Successful get all TODOs:** Verify a 200 OK status and correct JSON structure.
* [ ] **Test with offset and limit:** Verify correct pagination with various combinations of `offset` and `limit`.
* [ ] **Test with offset more than records:**  Verify behavior when `offset` exceeds the total number of TODOs.
* [ ] **Test with zero limit:** Verify that a limit of 0 returns an empty list.
* [ ] **Test with invalid offset and limit:** Verify error handling for negative or non-numeric values.

### `POST /todos`

* [X] **Successful creation of a TODO:** Verify a 200 OK status and retrieval of the created TODO.
* [X] **Test with empty text:** Verify behavior when the `text` field is empty.
* [X] **Test with max long text:** Verify behavior with character limits.
* [X] **Test with max long id:** Verify behavior with ID max value.
* [ ] **Test with over than max long text:** Verify behavior with exceeding character limits.
* [ ] **Test with different completed statuses:** Create TODOs with `completed` set to true and false.
* [ ] **Checklist for future tests:**
    * [ ] Test with special characters in the `text` field.
    * [ ] Load testing with concurrent requests via Gatling.

### `PUT /todos/:id`

* [ ] **Successful update of a TODO:** Verify a 200 OK status and retrieval of the updated TODO.
* [ ] **Test updating a non-existent TODO:** Verify appropriate error handling.
* [ ] **Test with empty text:** Verify behavior when the `text` field is updated to empty.
* [ ] **Test with changing completed status:**  Update the `completed` status from true to false and vice versa.
* [ ] **Checklist for future tests:**
    * [ ] Load testing with concurrent requests via Gatling.

### `DELETE /todos/:id`

* [ ] **Successful deletion of a TODO:** Verify a 204 OK status and that the TODO is no longer retrievable.
* [ ] **Test deleting a non-existent TODO:** Verify appropriate error handling.
* [ ] **Checklist for future tests:**
    * [ ] Test deleting a TODO that is referenced by other entities (if relationships exist).

### `/ws` (WebSocket)

* [ ] **Connection establishment:** Verify successful WebSocket connection.
* [ ] **Real-time updates:** Create a TODO via the REST API and verify that a notification is received via the WebSocket.
* [ ] **Checklist for future tests:**
    * [ ] Test different update notifications scenarios (update, delete).

### Performance Testing (`POST /todos`)

* [X] **Implement performance test**

* [ ] **Checklist for future performance improvements:**
    * [ ] Implement more performance tests using Gatling.
    * [ ] Generate performance reports with metrics like throughput and latency.
