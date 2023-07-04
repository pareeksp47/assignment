# Project

## Project Fields

| Field      | Description         | Type   | Non-null? | Editable? | Default |
| ---------- | ------------------- | ------ | --------- | --------- | ------- |
| id         | Project ID          | UUID   | x         |           |
| name       | Name of the project | String | x         | x         |
| floorplans | Floorplans project  | List   | x         | x         |

## Floorplan Fields

| Field        | Description                         | Type   | Non-null? | Editable? | Default |
| ------------ | ----------------------------------- | ------ | --------- | --------- | ------- |
| id           | Floor plan id                       | UUID   | x         |
| project      | Project floorplan belongs to        | UUID   | x         |           |
| name         | Name of the floorplan               | String | x         | x         |
| thumb_url    | thumb image url of the floorplan    | String | x         | x         |
| original_url | original image url of the floorplan | String | x         | x         |
| large_url    | large image url of the floorplan    | String | x         | x         |

## Swagger for API

```http
http://localhost:8080/api/swagger-ui/index.html#/
```

## Get Projects

```Curl
curl -X 'GET' \
  'http://localhost:8080/api/projects' \
  -H 'accept: */*'
```

> The above command returns:

```http
HTTP/1.1 200 OK
Content-Type: application/json

[
    {
        "id": "d94f128b-d5a0-4325-95d8-32457af9ab3b",
        "name": "Harrison"
    }
]
```

This endpoint retrieves all projects.

## Create Project

```Curl
curl -X 'POST' \
  'http://localhost:8080/api/projects' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "name": "Fieldwire"
}'
```

> The above command returns:

```http
HTTP/1.1 200 OK
Content-Type: application/json

{
  "statusCode": 201,
  "description": "Created Success"
}
```

This endpoint creats a project.

## Delete Project

```Curl
curl -X 'DELETE' \
  'http://localhost:8080/api/projects/972c2afe-99e8-445a-a202-572ee761ff2d' \
  -H 'accept: application/json'
```

> The above command returns:

```http
HTTP/1.1 200 OK
Content-Type: application/json

{
  "statusCode": 204,
  "description": "Record deleted successfully"
}
```

This endpoint deletes a project.

## Update Project

```Curl
curl -X 'PATCH' \
  'http://localhost:8080/api/projects/6308f499-fce0-4aa5-b7bd-8145d1325874' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "name": "Haryson"
}'
```

> The above command returns:

```http
HTTP/1.1 200 OK
Content-Type: application/json

{
  "statusCode": 204,
  "description": "Record updated successfully"
}
```

This endpoint updates a project.

## Get Floorplans for Project Id

### HTTP Request

`GET /projects/<Project ID>/floorplans`

### URL Parameters

| Parameter  | Description                       |
| ---------- | --------------------------------- |
| Project ID | The ID of the floorplan's project |

```Curl
curl -X 'GET' \
  'http://localhost:8080/api/projects/d94f128b-d5a0-4325-95d8-32457af9ab3b/floorplans' \
  -H 'accept: application/json'
```

> The above command returns:

```http
HTTP/1.1 200 OK
Content-Type: application/json

[
  {
    "id": "46324806-3854-4465-9b08-26f88246dd10",
    "name": "Screenshot 2023-06-18 190947.png",
    "originalUrl": "file:/D:/Users/floorplan/uploads/6308f499-fce0-4aa5-b7bd-8145d1325874/ORIGINALScreenshot%202023-06-18%20190947.png",
    "thumbUrl": "file:/D:/Users/floorplan/uploads/6308f499-fce0-4aa5-b7bd-8145d1325874/THUMBScreenshot%202023-06-18%20190947.png",
    "largeUrl": "file:/D:/Users/floorplan/uploads/6308f499-fce0-4aa5-b7bd-8145d1325874/LARGEScreenshot%202023-06-18%20190947.png",
    "files": null
  },
  {
    "id": "9b7e36ba-303c-4bfb-838f-92d9febb9991",
    "name": "Screenshot 2023-06-06 134353.png",
    "originalUrl": "file:/D:/Users/floorplan/uploads/6308f499-fce0-4aa5-b7bd-8145d1325874/ORIGINALScreenshot%202023-06-06%20134353.png",
    "thumbUrl": "file:/D:/Users/floorplan/uploads/6308f499-fce0-4aa5-b7bd-8145d1325874/THUMBScreenshot%202023-06-06%20134353.png",
    "largeUrl": "file:/D:/Users/floorplan/uploads/6308f499-fce0-4aa5-b7bd-8145d1325874/LARGEScreenshot%202023-06-06%20134353.png",
    "files": null
  }
]
```

This endpoint retrieves all floorplans for the project.

## Get a Floorplan for Project Id

### HTTP Request

`GET /projects/<Project ID>/floorplans/<Floorplan ID>`

### URL Parameters

| Parameter    | Description                         |
| ------------ | ----------------------------------- |
| Project ID   | The ID of the floorplan's project   |
| Floorplan ID | The ID of the floorplan to retrieve |

```Curl
curl -X 'GET' \
  'http://localhost:8080/api/projects/6308f499-fce0-4aa5-b7bd-8145d1325874/floorplans/9b7e36ba-303c-4bfb-838f-92d9febb9991' \
  -H 'accept: application/json'
```

> The above command returns:

```http
HTTP/1.1 200 OK
Content-Type: application/json

{
  "id": "9b7e36ba-303c-4bfb-838f-92d9febb9991",
  "name": "Screenshot 2023-06-06 134353.png",
  "originalUrl": "file:/D:/Users/floorplan/uploads/6308f499-fce0-4aa5-b7bd-8145d1325874/ORIGINALScreenshot%202023-06-06%20134353.png",
  "thumbUrl": "file:/D:/Users/floorplan/uploads/6308f499-fce0-4aa5-b7bd-8145d1325874/THUMBScreenshot%202023-06-06%20134353.png",
  "largeUrl": "file:/D:/Users/floorplan/uploads/6308f499-fce0-4aa5-b7bd-8145d1325874/LARGEScreenshot%202023-06-06%20134353.png",
  "files": null
}
```

This endpoint gets a floorplan for a project.

## Create Floorplans for Project Id

`POST /projects/<Project ID>/floorplans`

### URL Parameters

| Parameter  | Description                       |
| ---------- | --------------------------------- |
| Project ID | The ID of the floorplan's project |

```Curl
curl -X 'POST' \
  'http://localhost:8080/api/projects/6308f499-fce0-4aa5-b7bd-8145d1325874/floorplans' \
  -H 'accept: application/json' \
  -H 'Content-Type: multipart/form-data' \
  -F 'files=@Screenshot 2023-06-19 153448.png;type=image/png'
```

> The above command returns:

```http
HTTP/1.1 200 OK
Content-Type: application/json

{
  "statusCode": 201,
  "description": "Request successful"
}
```

This endpoint creates floorplans for a project.

## Delete Floorplan for the Project

`DELETE /projects/<Project ID>/floorplans/<Floorplan ID>`

### URL Parameters

| Parameter    | Description                         |
| ------------ | ----------------------------------- |
| Project ID   | The ID of the floorplan's project   |
| Floorplan ID | The ID of the floorplan to retrieve |

```Curl
curl -X 'DELETE' \
  'http://localhost:8080/api/projects/972c2afe-99e8-445a-a202-572ee761ff2d/floorplans/9b7e36ba-303c-4bfb-838f-92d9febb9991' \
  -H 'accept: application/json'
```

> The above command returns:

```http
HTTP/1.1 200 OK
Content-Type: application/json

{
  "statusCode": 204,
  "description": "Record deleted successfully"
}
```

This endpoint deletes a floorplan for the project.

## Update Floorplan for the Project

`PATCH /projects/<Project ID>/floorplans/<Floorplan ID>`

### URL Parameters

| Parameter    | Description                         |
| ------------ | ----------------------------------- |
| Project ID   | The ID of the floorplan's project   |
| Floorplan ID | The ID of the floorplan to retrieve |

```Curl
curl -X 'PATCH' \
  'http://localhost:8080/api/projects/6308f499-fce0-4aa5-b7bd-8145d1325874/floorplans/9b7e36ba-303c-4bfb-838f-92d9febb9991' \
  -H 'accept: application/json' \
  -H 'Content-Type: multipart/form-data' \
  -F 'files=@Screenshot 2023-06-06 133220.png;type=image/png'
```

> The above command returns:

```http
HTTP/1.1 200 OK
Content-Type: application/json

{
  "statusCode": 204,
  "description": "Record updated successfully"
}
```

This endpoint updates a floorplan for the project.
