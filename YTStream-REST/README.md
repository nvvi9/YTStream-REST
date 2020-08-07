# YTStream REST API
REST API for YTStream library
### Get video data array
Video data includes video details (title, description, thumbnails, etc.) and streams information (type, extension, codecs, streaming url, etc.)
#### Request
`GET /videodata?id=:id`

Where `id` is single or multiple YouTube video IDs separated by a comma or plus sign.

`https://stream-yt.herokuapp.com/api/v1/videodata?id=w8KQmps-Sog+1nX0kF2UwDc`
#### Response

```
[ {
    "videoDetails" : {
      "id" : "w8KQmps-Sog",
      "title" : "Muse - Uprising [Official Video]",
      "channel" : "Muse",
      "channelId" : "UCGGhM6XCSJFQ6DTRffnKRIw",
      "description" : "Watch the music video for \\",
      "durationSeconds" : 252,
      "viewCount" : 196729057,
      "thumbnails" : [ {
        "width" : 120,
        "height" : 90,
        "url" : "https://img.youtube.com/vi/w8KQmps-Sog/default.jpg"
      }, {
        "width" : 320,
        "height" : 180,
        "url" : "https://img.youtube.com/vi/w8KQmps-Sog/mqdefault.jpg"
      }, {
        "width" : 480,
        "height" : 360,
        "url" : "https://img.youtube.com/vi/w8KQmps-Sog/hqdefault.jpg"
      } ],
      "expiresInSeconds" : 21540,
      "isLiveStream" : false
    },
    "streams" : [ 
      stream array  
   ]
  }, {
  "videoDetails" : {
    "id" : "1nX0kF2UwDc",
    "title" : "[BadComedian] - Движение Вверх (Плагиат или великая правда?)",
    "channel" : "BadComedian",
    "channelId" : "UC6cqazSR6CnVMClY0bJI0Lg",
    "description" : "#BadComedian обзор фильма Движение Вверх, который и великая правда, и плагиат. \\nПоддержать создание обзоров - http://TheBadComedian.ru/Za_SebyaiZaSashky\\n========================\\nГруппа ВК - http://vk.com/badcomedian \\nСтраница FB - https://www.facebook.com/thebadcomedian\\nТвиттер - http://twitter.com/EvgenComedian\\nИнстаграм - http://instagram.com/evgenbad\\nОсновной канал - http://www.youtube.com/TheBadComedian\\nВторой Канал - http://www.youtube.com/EvgenComedian\\n===========================",
    "durationSeconds" : 8312,
    "viewCount" : 23862618,
    "thumbnails" : [ {
      "width" : 120,
      "height" : 90,
      "url" : "https://img.youtube.com/vi/1nX0kF2UwDc/default.jpg"
    }, {
      "width" : 320,
      "height" : 180,
      "url" : "https://img.youtube.com/vi/1nX0kF2UwDc/mqdefault.jpg"
    }, {
      "width" : 480,
      "height" : 360,
      "url" : "https://img.youtube.com/vi/1nX0kF2UwDc/hqdefault.jpg"
    } ],
    "expiresInSeconds" : 21540,
    "isLiveStream" : false
  },
  "streams" : [ 
    stream array
 ]
} ]
```
### Get video details array
Response will contain only video details without streams. In this case processing will take less time.
#### Request
`GET /videodetails?id=:id`

`https://stream-yt.herokuapp.com/api/v1/videodetails?id=1nX0kF2UwDc+w8KQmps-Sog`
#### Response
```
[ {
  "id" : "w8KQmps-Sog",
  "title" : "Muse - Uprising [Official Video]",
  "channel" : "Muse",
  "channelId" : "UCGGhM6XCSJFQ6DTRffnKRIw",
  "description" : "Watch the music video for \\",
  "durationSeconds" : 252,
  "viewCount" : 196730443,
  "thumbnails" : [ {
    "width" : 120,
    "height" : 90,
    "url" : "https://img.youtube.com/vi/w8KQmps-Sog/default.jpg"
  }, {
    "width" : 320,
    "height" : 180,
    "url" : "https://img.youtube.com/vi/w8KQmps-Sog/mqdefault.jpg"
  }, {
    "width" : 480,
    "height" : 360,
    "url" : "https://img.youtube.com/vi/w8KQmps-Sog/hqdefault.jpg"
  } ],
  "expiresInSeconds" : 21540,
  "isLiveStream" : false
}, {
  "id" : "1nX0kF2UwDc",
  "title" : "[BadComedian] - Движение Вверх (Плагиат или великая правда?)",
  "channel" : "BadComedian",
  "channelId" : "UC6cqazSR6CnVMClY0bJI0Lg",
  "description" : "#BadComedian обзор фильма Движение Вверх, который и великая правда, и плагиат. \\nПоддержать создание обзоров - http://TheBadComedian.ru/Za_SebyaiZaSashky\\n========================\\nГруппа ВК - http://vk.com/badcomedian \\nСтраница FB - https://www.facebook.com/thebadcomedian\\nТвиттер - http://twitter.com/EvgenComedian\\nИнстаграм - http://instagram.com/evgenbad\\nОсновной канал - http://www.youtube.com/TheBadComedian\\nВторой Канал - http://www.youtube.com/EvgenComedian\\n===========================",
  "durationSeconds" : 8312,
  "viewCount" : 23862810,
  "thumbnails" : [ {
    "width" : 120,
    "height" : 90,
    "url" : "https://img.youtube.com/vi/1nX0kF2UwDc/default.jpg"
  }, {
    "width" : 320,
    "height" : 180,
    "url" : "https://img.youtube.com/vi/1nX0kF2UwDc/mqdefault.jpg"
  }, {
    "width" : 480,
    "height" : 360,
    "url" : "https://img.youtube.com/vi/1nX0kF2UwDc/hqdefault.jpg"
  } ],
  "expiresInSeconds" : 21540,
  "isLiveStream" : false
} ]
```
