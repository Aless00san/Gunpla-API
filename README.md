
## API Reference

### Gunpla
#### Get all gunpla

```http
  GET /api/gunpla/list
```

#### Get gunpla by id

```http
  GET /api/gunpla/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `long` | **Required**. Id of gunpla to fetch |

#### Get gunpla by grade

```http
  GET /api/gunpla?grade=x
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `grade`      | `string` | **Required**. Grade of gunpla to fetch |

#### Get gunpla by series

```http
  GET /api/gunpla?series=x
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `series`      | `string` | **Required**. Series of gunpla to fetch |

#### Delete gunpla by id

```http
  DELETE /api/gunpla/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `long` | **Required**. Id of gunpla to delete |

#### Add a gunpla entry

```http
  POST /api/gunpla/
```

| Body | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `gunpla`      | `JSON` | **Required**. Gunpla to create |

#### Update a gunpla entry

```http
  PUT /api/gunpla/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `long` | **Required**. Id of gunpla to update |

#### Gets a page of given size of gunpla

```http
  GET /api/gunpla/list?page=x&size=y
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `page`      | `int` | **Required**. Starting page, 0 is first page|
| `size`      | `int` | **Required**. Size of the page it will return|

### Series

#### Get all series

```http
  GET /api/series/list
```

#### Get series by id

```http
  GET /api/series/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `long` | **Required**. Id of series to fetch |

#### Get series by name

```http
  GET /api/series?name=x
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `name`      | `string` | **Required**. Name of series to fetch |

#### Adds a series entry

```http
  POST /api/series/create
```

| Body | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `series`      | `JSON` | **Required**. Series to create |

### Grade

#### Get all grades

```http
  GET /api/grade/list
```

#### Get grades by id

```http
  GET /api/grade/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `long` | **Required**. Id of grades to fetch |

#### Get grades by name

```http
  GET /api/grades?name=x
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `name`      | `string` | **Required**. Name of grades to fetch |

#### Adds a grade entry

```http
  POST /api/grade/create
```

| Body | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `grade`      | `JSON` | **Required**. Grade to create |

#### Deletes a grade entry

```http
  DELETE /api/grade/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `JSON` | **Required**. Id of the grade to delete |

### User

#### Get all users

```http
  GET /api/user/list
```

#### Register a new user

```http
  POST /api/user/register
```

| Body | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `user`      | `JSON` | **Required**. User to register |

#### Logout from active session

```http
  POST /api/user/logout
```

#### Checks for auth

```http
  GET /api/user/auth
```
