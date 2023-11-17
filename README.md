## SOAP Service for Tonality
This SOAP service is used to manage subscription requests for premium albums from Tonality. It is also used for loggin these requests.

## Database Schema
| subscription        |
|---------------------|
| premium_album_id    |
| user_id             |
| album_name          |
| artist              |
| album_name          |
| created_at          |
| subscription_status |
| updated_at          |
| username            |

Types:\
`premium_album_id` bigint NOT NULL\
`user_id` bigint NOT NULL\
`album_name` varchar(255) NOT NULL\
`artist` varchar(255) NOT NULL\
`created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6)\
`subscription_status` varchar(255) NOT NULL DEFAULT 'PENDING'\
`updated_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6)\
`username` varchar(255) NOT NULL\
PRIMARY KEY (`premium_album_id`,`user_id`)

| logging     |
|-------------|
| log_id      |
| ip          |
| description |
| endpoint    |

Types:\
`log_id` bigint NOT NULL\
`ip` varchar(255) NOT NULL\
`description` varchar(255) NOT NULL\
`endpoint` varchar(255) NOT NULL\
PRIMARY KEY (`log_id`)

## API Endpoint
`/subscription`

## Task Distribution
| Task                | Student ID |
|---------------------|------------|
| Subscription        | 13521087   |
| Logging             | 13521063   |
| Environment & Build | 13521096   |