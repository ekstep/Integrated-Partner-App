package org.ekstep.genie.util;

import android.support.annotation.NonNull;

import org.ekstep.genieservices.commons.utils.StringUtil;

public class SampleData {

    @NonNull
    public static String contentApiResponse(String ttlTime) {
        return "{\n" +
                "  \"id\": \"ekstep.content.list\",\n" +
                "  \"ver\": \"2.0\",\n" +
                "  \"ts\": \"2016-03-25T07:01:05ZZ\",\n" +
                "  \"params\": {\n" +
                "    \"resmsgid\": \"e6c55190-6295-4821-ba9e-cc51fbc7e04e\",\n" +
                "    \"msgid\": null,\n" +
                "    \"err\": null,\n" +
                "    \"status\": \"successful\",\n" +
                "    \"errmsg\": null\n" +
                "  },\n" +
                "  \"responseCode\": \"OK\",\n" +
                "  \"result\": {\n" +
                "    \"content\": [\n" +
                "      {\n" +
                "        \"appIcon\": \"https://s3-ap-southeast-1.amazonaws.com/ekstep-public/games/1445005497671_app-icon.png\",\n" +
                "        \"status\": \"Live\",\n" +
                "        \"posterImage\": \"https://s3-ap-southeast-1.amazonaws.com/ekstep-public/games/quiz-icon.png\",\n" +
                "        \"launchUrl\": \"org.ekstep.quiz.app\",\n" +
                "        \"activity_class\": \".MainActivity\",\n" +
                "        \"code\": \"org.ekstep.quiz.app\",\n" +
                "        \"contentType\": \"Game\",\n" +
                "        \"lastUpdatedOn\": \"2016-02-09T10:39:20.042+0000\",\n" +
                "        \"osId\": \"org.ekstep.quiz.app\",\n" +
                "        \"name\": \"EkStep Content App\",\n" +
                "        \"owner\": \"EkStep\",\n" +
                "        \"identifier\": \"numeracy_374\",\n" +
                "        \"mimeType\": \"application/vnd.android.package-archive\",\n" +
                "        \"grayScaleAppIcon\": \"https://s3-ap-southeast-1.amazonaws.com/ekstep-public/games/quiz-icon-gs.png\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"ttl\": " + ttlTime + "\n" +
                "  }\n" +
                "}";
    }

    public static String contentApiResponseWithTwoContents(String ttlTime) {
        return "{\n" +
                "  \"id\": \"ekstep.content.list\",\n" +
                "  \"ver\": \"2.0\",\n" +
                "  \"ts\": \"2016-03-25T07:01:05ZZ\",\n" +
                "  \"params\": {\n" +
                "    \"resmsgid\": \"e6c55190-6295-4821-ba9e-cc51fbc7e04e\",\n" +
                "    \"msgid\": null,\n" +
                "    \"err\": null,\n" +
                "    \"status\": \"successful\",\n" +
                "    \"errmsg\": null\n" +
                "  },\n" +
                "  \"responseCode\": \"OK\",\n" +
                "  \"result\": {\n" +
                "    \"content\": [\n" +
                "      {\n" +
                "        \"appIcon\": \"https://s3-ap-southeast-1.amazonaws.com/ekstep-public/games/1445005497671_app-icon.png\",\n" +
                "        \"status\": \"Live\",\n" +
                "        \"posterImage\": \"https://s3-ap-southeast-1.amazonaws.com/ekstep-public/games/quiz-icon.png\",\n" +
                "        \"launchUrl\": \"org.ekstep.quiz.app\",\n" +
                "        \"activity_class\": \".MainActivity\",\n" +
                "        \"code\": \"org.ekstep.quiz.app\",\n" +
                "        \"contentType\": \"Game\",\n" +
                "        \"lastUpdatedOn\": \"2016-02-09T10:39:20.042+0000\",\n" +
                "        \"osId\": \"org.ekstep.quiz.app\",\n" +
                "        \"name\": \"EkStep Content App\",\n" +
                "        \"owner\": \"EkStep\",\n" +
                "        \"identifier\": \"numeracy_374\",\n" +
                "        \"mimeType\": \"application/vnd.android.package-archive\",\n" +
                "        \"grayScaleAppIcon\": \"https://s3-ap-southeast-1.amazonaws.com/ekstep-public/games/quiz-icon-gs.png\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"tags\": [\n" +
                "          \"counting\",\n" +
                "          \"apples\"\n" +
                "        ],\n" +
                "        \"appIcon\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/language_assets/Apples_1459227166949.jpg\",\n" +
                "        \"visibility\": \"Default\",\n" +
                "        \"status\": \"Live\",\n" +
                "        \"downloadUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/domain_14439_1459234823889.ecar\",\n" +
                "        \"contentType\": \"Story\",\n" +
                "        \"code\": \"org.ekstep.numeracy.story.174\",\n" +
                "        \"lastUpdatedOn\": \"2016-03-29T07:00:24.002+0000\",\n" +
                "        \"createdOn\": \"2016-03-29T04:52:46.890+0000\",\n" +
                "        \"osId\": \"org.ekstep.quiz.app\",\n" +
                "        \"collaborators\": [\n" +
                "          \"239\",\n" +
                "          \"143\"\n" +
                "        ],\n" +
                "        \"description\": \"Counting Apples for Children\",\n" +
                "        \"name\": \"Counting Apples\",\n" +
                "        \"owner\": \"EkStep\",\n" +
                "        \"artifactUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/1459234823378_domain_14439.zip\",\n" +
                "        \"language\": [\n" +
                "          \"English\"\n" +
                "        ],\n" +
                "        \"identifier\": \"domain_14439\",\n" +
                "        \"portalOwner\": \"EkStep\",\n" +
                "        \"mimeType\": \"application/vnd.ekstep.ecml-archive\",\n" +
                "        \"pkgVersion\": 1\n" +
                "      }" +
                "    ],\n" +
                "    \"ttl\": " + ttlTime + "\n" +
                "  }\n" +
                "}";
    }

    public static String telemetrySyncResponse(final String status) {
        return "{\n" +
                "  \"id\": \"ekstep.telemetry\",\n" +
                "  \"ver\": \"1.0\",\n" +
                "  \"ts\": \"2016-03-28T15:25:49+05:30\",\n" +
                "  \"params\": {\n" +
                "    \"resmsgid\": \"ff3f6feee246c13d44558623c3b426d4aefb9343\",\n" +
                "    \"msgid\": \"3BE38CAFADC951C3E87D2BE0FDD06144507BB8912\",\n" +
                "    \"status\": \"" + status + "\",\n" +
                "    \"err\": \"\",\n" +
                "    \"errmsg\": \"\"\n" +
                "  }\n" +
                "}";
    }

    @NonNull
    public static String telemetryEvent(String extraField) {
        return "{\n" +
                "  \"tags\": [],\n" +
                "  \"uid\": \"1cfb7005-1dca-4d17-a955-b6a15567cd63\",\n" +
                "  \"sid\": \"a2f668e0-e8fb-48ba-a860-37dda8f9a9a8\",\n" +
                extraField +
                "  \"edata\": {\n" +
                "    \"eks\": {\n" +
                "      \"length\": 115.0\n" +
                "    }\n" +
                "  },\n" +
                "  \"did\": \"f55a95e549de98cc51bd6c08eaa438b61db3b92f\",\n" +
                "  \"ver\": \"1.0\",\n" +
                "  \"type\": \"events\",\n" +
                "  \"eid\": \"GE_SESSION_END\",\n" +
                "  \"@version\": \"1\",\n" +
                "  \"gdata\": {\n" +
                "    \"id\": \"genieservice.android\",\n" +
                "    \"ver\": \"1.0.166-qa\"\n" +
                "  },\n" +
                "  \"@timestamp\": \"2015-10-28T10:11:22.602Z\",\n" +
                "  \"uuid\": \"02ff81bd-bf7f-4a66-8b64-adeedf8abef430\",\n" +
                "  \"key\": \"1cfb7005-1dca-4d17-a955-b6a15567cd63\",\n" +
                "  \"metadata\": {\n" +
                "    \"checksum\": \"13de25a71f8ceff679ea894d8db14c702e4933ef\",\n" +
                "    \"last_processed_at\": \"2015-10-28T15:42:04.198+05:30\",\n" +
                "    \"processed_count\": 1\n" +
                "  },\n" +
                "  \"ldata\": {\n" +
                "    \"state\": \"Karnataka\",\n" +
                "    \"locality\": \"Bengaluru\",\n" +
                "    \"district\": \"Bangalore Urban\",\n" +
                "    \"country\": \"India\"\n" +
                "  },\n" +
                "  \"flags\": {\n" +
                "    \"ldata_processed\": true,\n" +
                "    \"ldata_obtained\": true\n" +
                "  }\n" +
                "}";
    }

    @NonNull
    public static String telemetry_OE_END_Event() {
        return "{\"uid\":\"034b4b65-59e7-4d8a-989b-e33a4f021538\",\"sid\":\"d83b7f2d-cda5-4130-b111-ede7ca903c69\",\"ts\":\"\",\"edata\":{\"eks\":{\"length\":20.0}},\"did\":\"a3ad47b702db0b8377507218e796a5f4becf7d6d\",\"ver\":\"2.0\",\"eid\":\"OE_END\",\"gdata\":{\"id\":\"org.akshara\",\"ver\":\"1.6.7.2\"},\"ets\":1466744608326,\"mid\":\"bf12e9a6-3f65-4480-9c1e-258a4f60e796\"}";
    }

    public static String emptyContentSearchResponseWithFacets() {
        return "{\n" +
                "  \"id\": \"ekstep.composite-search.search\",\n" +
                "  \"ver\": \"1.0\",\n" +
                "  \"ts\": \"2016-05-05T07:58:20ZZ\",\n" +
                "  \"params\": {\n" +
                "    \"resmsgid\": \"1ff12185-232f-46f8-bfef-f492170d2988\",\n" +
                "    \"msgid\": null,\n" +
                "    \"err\": null,\n" +
                "    \"status\": \"Success\",\n" +
                "    \"errmsg\": null\n" +
                "  },\n" +
                "  \"responseCode\": \"OK\",\n" +
                "  \"result\": {\n" +
                "    \"facets\": [\n" +
                "      {\n" +
                "        \"values\": [],\n" +
                "        \"name\": \"domain\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"values\": [],\n" +
                "        \"name\": \"gradeLevel\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"values\": [],\n" +
                "        \"name\": \"language\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"values\": [],\n" +
                "        \"name\": \"contentType\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"values\": [],\n" +
                "        \"name\": \"ageGroup\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}";
    }

    public static String emptyResponse(final String id) {
        return "{\n" +
                "  \"id\": \"" + id + "\",\n" +
                "  \"ver\": \"1.0\",\n" +
                "  \"ts\": \"2016-05-03T04:57:07ZZ\",\n" +
                "  \"params\": {\n" +
                "    \"resmsgid\": \"b9cd7e18-b71b-4eff-b143-72d3442d404e\",\n" +
                "    \"msgid\": \"\",\n" +
                "    \"err\": \"\",\n" +
                "    \"status\": \"Success\",\n" +
                "    \"errmsg\": \"\"\n" +
                "  },\n" +
                "  \"responseCode\": \"OK\",\n" +
                "  \"result\": {}\n" +
                "}";
    }

    public static String contentDomainSearchResponse() {
        return "{\n" +
                "    \"id\": \"ekstep.composite-search.search\",\n" +
                "    \"ver\": \"2.0\",\n" +
                "    \"ts\": \"2016-08-30T10:24:47ZZ\",\n" +
                "    \"params\": {\n" +
                "        \"resmsgid\": \"8801c20f-d155-44b0-adc4-e37eda0ceadd\",\n" +
                "        \"msgid\": null,\n" +
                "        \"err\": null,\n" +
                "        \"status\": \"successful\",\n" +
                "        \"errmsg\": null\n" +
                "    },\n" +
                "    \"responseCode\": \"OK\",\n" +
                "    \"result\": {\n" +
                "        \"count\": 2,\n" +
                "        \"content\": [\n" +
                "            {\n" +
                "                \"appIcon\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/l_1467787080888.thumb.png\",\n" +
                "                \"idealScreenSize\": \"normal\",\n" +
                "                \"visibility\": \"Default\",\n" +
                "                \"contentType\": \"Worksheet\",\n" +
                "                \"mediaType\": \"content\",\n" +
                "                \"node_id\": 32995,\n" +
                "                \"publisher\": \"\",\n" +
                "                \"osId\": \"org.ekstep.quiz.app\",\n" +
                "                \"graph_id\": \"domain\",\n" +
                "                \"description\": \"This worksheet contains 10 questions. It is for students learning addition up to 99. For each question, feedback is provided.\\r\\n\",\n" +
                "                \"name\": \"numbers\",\n" +
                "                \"domain\": [\n" +
                "                    \"numeracy\"\n" +
                "                ],\n" +
                "                \"lastSubmittedOn\": \"2016-07-05T07:07:37.718+0000\",\n" +
                "                \"nodeType\": \"DATA_NODE\",\n" +
                "                \"license\": \"Creative Commons Attribution (CC BY)\",\n" +
                "                \"mimeType\": \"application/vnd.ekstep.ecml-archive\",\n" +
                "                \"ageGroup\": [\n" +
                "                    \"7-8\",\n" +
                "                    \"8-10\"\n" +
                "                ],\n" +
                "                \"tags\": [\n" +
                "                    \"sum\",\n" +
                "                    \"three numbers\",\n" +
                "                    \"addition of three numbers\",\n" +
                "                    \"add\",\n" +
                "                    \"addition\"\n" +
                "                ],\n" +
                "                \"os\": [\n" +
                "                    \"All\"\n" +
                "                ],\n" +
                "                \"idealScreenDensity\": \"hdpi\",\n" +
                "                \"status\": \"Live\",\n" +
                "                \"code\": \"org.ekstep.numeracy.worksheet.705\",\n" +
                "                \"copyright\": \"CC0\",\n" +
                "                \"lastUpdatedOn\": \"2016-08-02T11:50:28.273+0000\",\n" +
                "                \"concepts\": [\n" +
                "                    \"Num:C3:SC1\"\n" +
                "                ],\n" +
                "                \"soundCredits\": [\n" +
                "                    \"ekstep\"\n" +
                "                ],\n" +
                "                \"createdOn\": \"2016-07-05T07:02:12.101+0000\",\n" +
                "                \"source\": \"EkStep\",\n" +
                "                \"lastUpdatedBy\": \"80\",\n" +
                "                \"owner\": \"Arinjay Singhai\",\n" +
                "                \"imageCredits\": [\n" +
                "                    \"ekstep\",\n" +
                "                    \"EkStep\"\n" +
                "                ],\n" +
                "                \"gradeLevel\": [\n" +
                "                    \"Grade 3\",\n" +
                "                    \"Grade 4\",\n" +
                "                    \"Grade 5\"\n" +
                "                ],\n" +
                "                \"language\": [\n" +
                "                    \"English\"\n" +
                "                ],\n" +
                "                \"objectType\": \"Content\",\n" +
                "                \"identifier\": \"do_30032995\",\n" +
                "                \"portalOwner\": \"385\",\n" +
                "                \"lastPublishedOn\": \"2016-08-02T11:50:28.209+0000\",\n" +
                "                \"posterImage\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/language_assets/l_1467787080888.png\",\n" +
                "                \"downloadUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/add-three-numbers-sums-to-99_1470138627927_do_30032995.ecar\",\n" +
                "                \"artifactUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/1470138625487_do_30032995.zip\",\n" +
                "                \"pkgVersion\": 2,\n" +
                "                \"size\": 1276958,\n" +
                "                \"es_metadata_id\": \"do_30032995\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"appIcon\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/language_assets/f_1467791964981.png\",\n" +
                "                \"idealScreenSize\": \"normal\",\n" +
                "                \"visibility\": \"Default\",\n" +
                "                \"contentType\": \"Worksheet\",\n" +
                "                \"mediaType\": \"content\",\n" +
                "                \"node_id\": 30419,\n" +
                "                \"publisher\": \"\",\n" +
                "                \"osId\": \"org.ekstep.quiz.app\",\n" +
                "                \"graph_id\": \"domain\",\n" +
                "                \"description\": \"This worksheet contains 10 questions. It is for students learning addition up to 20. For each question, feedback is provided.\\r\\n\",\n" +
                "                \"name\": \"numbers\",\n" +
                "                \"domain\": [\n" +
                "                    \"numeracy\"\n" +
                "                ],\n" +
                "                \"lastSubmittedOn\": \"2016-07-01T06:15:03.462+0000\",\n" +
                "                \"nodeType\": \"DATA_NODE\",\n" +
                "                \"license\": \"Creative Commons Attribution (CC BY)\",\n" +
                "                \"mimeType\": \"application/vnd.ekstep.ecml-archive\",\n" +
                "                \"ageGroup\": [\n" +
                "                    \"6-7\",\n" +
                "                    \"7-8\",\n" +
                "                    \"8-10\"\n" +
                "                ],\n" +
                "                \"tags\": [\n" +
                "                    \"three numbers\",\n" +
                "                    \"add\",\n" +
                "                    \"sum\",\n" +
                "                    \"addition\",\n" +
                "                    \"20\",\n" +
                "                    \"up to 20\"\n" +
                "                ],\n" +
                "                \"os\": [\n" +
                "                    \"All\"\n" +
                "                ],\n" +
                "                \"idealScreenDensity\": \"hdpi\",\n" +
                "                \"status\": \"Live\",\n" +
                "                \"code\": \"org.ekstep.numeracy.worksheet.658\",\n" +
                "                \"copyright\": \"CC0\",\n" +
                "                \"lastUpdatedOn\": \"2016-07-13T12:16:31.231+0000\",\n" +
                "                \"concepts\": [\n" +
                "                    \"Num:C3:SC1\"\n" +
                "                ],\n" +
                "                \"soundCredits\": [\n" +
                "                    \"ekstep\"\n" +
                "                ],\n" +
                "                \"createdOn\": \"2016-07-01T06:05:24.024+0000\",\n" +
                "                \"source\": \"EkStep\",\n" +
                "                \"lastUpdatedBy\": \"80\",\n" +
                "                \"owner\": \"Arinjay Singhai\",\n" +
                "                \"imageCredits\": [\n" +
                "                    \"ekstep\",\n" +
                "                    \"EkStep\"\n" +
                "                ],\n" +
                "                \"gradeLevel\": [\n" +
                "                    \"Grade 2\",\n" +
                "                    \"Grade 3\",\n" +
                "                    \"Grade 4\",\n" +
                "                    \"Grade 5\"\n" +
                "                ],\n" +
                "                \"language\": [\n" +
                "                    \"English\"\n" +
                "                ],\n" +
                "                \"objectType\": \"Content\",\n" +
                "                \"identifier\": \"do_30030419\",\n" +
                "                \"portalOwner\": \"385\",\n" +
                "                \"lastPublishedOn\": \"2016-07-13T12:16:30.812+0000\",\n" +
                "                \"downloadUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/do_30030419_1468412190443.ecar\",\n" +
                "                \"artifactUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/1468412188712_do_30030419.zip\",\n" +
                "                \"pkgVersion\": 1,\n" +
                "                \"size\": 1318198,\n" +
                "                \"es_metadata_id\": \"do_30030419\"\n" +
                "            }\n" +
                "        ],\n" +
                "        \"facets\": [\n" +
                "            {\n" +
                "                \"values\": [\n" +
                "                    {\n" +
                "                        \"name\": \"grade 2\",\n" +
                "                        \"count\": 1\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"name\": \"grade 3\",\n" +
                "                        \"count\": 2\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"name\": \"grade 4\",\n" +
                "                        \"count\": 2\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"name\": \"grade 5\",\n" +
                "                        \"count\": 2\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"name\": \"gradeLevel\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"values\": [\n" +
                "                    {\n" +
                "                        \"name\": \"numeracy\",\n" +
                "                        \"count\": 2\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"name\": \"domain\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"values\": [\n" +
                "                    {\n" +
                "                        \"name\": \"english\",\n" +
                "                        \"count\": 2\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"name\": \"language\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"values\": [\n" +
                "                    {\n" +
                "                        \"name\": \"6-7\",\n" +
                "                        \"count\": 1\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"name\": \"7-8\",\n" +
                "                        \"count\": 2\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"name\": \"8-10\",\n" +
                "                        \"count\": 2\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"name\": \"ageGroup\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"values\": [\n" +
                "                    {\n" +
                "                        \"name\": \"worksheet\",\n" +
                "                        \"count\": 2\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"name\": \"contentType\"\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}";
    }

    public static String contentNewestresponse() {
        return "{\n" +
                "    \"id\": \"ekstep.composite-search.search\",\n" +
                "    \"ver\": \"2.0\",\n" +
                "    \"ts\": \"2016-08-30T10:28:24ZZ\",\n" +
                "    \"params\": {\n" +
                "        \"resmsgid\": \"92faa272-dd36-4db5-94ba-3630366d04bd\",\n" +
                "        \"msgid\": null,\n" +
                "        \"err\": null,\n" +
                "        \"status\": \"successful\",\n" +
                "        \"errmsg\": null\n" +
                "    },\n" +
                "    \"responseCode\": \"OK\",\n" +
                "    \"result\": {\n" +
                "        \"count\": 1,\n" +
                "        \"content\": [\n" +
                "            {\n" +
                "                \"code\": \"Test_QA\",\n" +
                "                \"description\": \"Test_QA\",\n" +
                "                \"language\": [\n" +
                "                    \"English\"\n" +
                "                ],\n" +
                "                \"mimeType\": \"application/vnd.ekstep.ecml-archive\",\n" +
                "                \"idealScreenSize\": \"normal\",\n" +
                "                \"createdOn\": \"2016-08-30T03:59:51.818+0000\",\n" +
                "                \"objectType\": \"Content\",\n" +
                "                \"gradeLevel\": [\n" +
                "                    \"Grade 1\"\n" +
                "                ],\n" +
                "                \"lastUpdatedOn\": \"2016-08-30T03:59:55.654+0000\",\n" +
                "                \"contentType\": \"Story\",\n" +
                "                \"owner\": \"EkStep\",\n" +
                "                \"identifier\": \"LP_FT_T_594\",\n" +
                "                \"os\": [\n" +
                "                    \"All\"\n" +
                "                ],\n" +
                "                \"visibility\": \"Default\",\n" +
                "                \"mediaType\": \"content\",\n" +
                "                \"osId\": \"org.ekstep.quiz.app\",\n" +
                "                \"ageGroup\": [\n" +
                "                    \"5-6\"\n" +
                "                ],\n" +
                "                \"graph_id\": \"domain\",\n" +
                "                \"nodeType\": \"DATA_NODE\",\n" +
                "                \"pkgVersion\": 4,\n" +
                "                \"license\": \"Creative Commons Attribution (CC BY)\",\n" +
                "                \"idealScreenDensity\": \"hdpi\",\n" +
                "                \"name\": \"LP_FT_T-594\",\n" +
                "                \"status\": \"Live\",\n" +
                "                \"node_id\": 45951,\n" +
                "                \"tags\": [\n" +
                "                    \"LP_functionalTest\"\n" +
                "                ],\n" +
                "                \"artifactUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/ecml_with_json_1472529593507.zip\",\n" +
                "                \"size\": 1227692,\n" +
                "                \"lastPublishedOn\": \"2016-08-30T03:59:55.583+0000\",\n" +
                "                \"downloadUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/lp_ft_t-594_1472529595286_lp_ft_t_594.ecar\",\n" +
                "                \"collections\": [\n" +
                "                    \"LP_FT_Collection4847028\"\n" +
                "                ],\n" +
                "                \"es_metadata_id\": \"LP_FT_T_594\"\n" +
                "            }\n" +
                "        ],\n" +
                "        \"facets\": [\n" +
                "            {\n" +
                "                \"values\": [\n" +
                "                    {\n" +
                "                        \"name\": \"grade 1\",\n" +
                "                        \"count\": 1\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"name\": \"gradeLevel\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"values\": [],\n" +
                "                \"name\": \"domain\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"values\": [\n" +
                "                    {\n" +
                "                        \"name\": \"english\",\n" +
                "                        \"count\": 1\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"name\": \"language\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"values\": [\n" +
                "                    {\n" +
                "                        \"name\": \"5-6\",\n" +
                "                        \"count\": 1\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"name\": \"ageGroup\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"values\": [\n" +
                "                    {\n" +
                "                        \"name\": \"story\",\n" +
                "                        \"count\": 1\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"name\": \"contentType\"\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}";
    }

    public static String searchResponse() {
        return "{\n" +
                "  \"id\": \"ekstep.composite-search.search\",\n" +
                "  \"ver\": \"3.0\",\n" +
                "  \"ts\": \"2017-07-28T05:29:13ZZ\",\n" +
                "  \"params\": {\n" +
                "    \"resmsgid\": \"ff2ce054-5fec-4579-b14f-fa452f5eb525\",\n" +
                "    \"msgid\": null,\n" +
                "    \"err\": null,\n" +
                "    \"status\": \"successful\",\n" +
                "    \"errmsg\": null\n" +
                "  },\n" +
                "  \"responseCode\": \"OK\",\n" +
                "  \"result\": {\n" +
                "    \"count\": 4,\n" +
                "    \"content\": [\n" +
                "      {\n" +
                "        \"copyright\": \"CC0\",\n" +
                "        \"code\": \"org.ekstep.numeracy.worksheet.471\",\n" +
                "        \"imageCredits\": [\n" +
                "          \"ekstep\",\n" +
                "          \"Parabal Singh\",\n" +
                "          \"EkStep\"\n" +
                "        ],\n" +
                "        \"downloadUrl\": \"https://ekstep-public-qa.s3-ap-south-1.amazonaws.com/ecar_files/do_30013062_1467630762635.ecar\",\n" +
                "        \"description\": \"इस वर्कशीट में एक-अंकीय संख्याओं के जोड़ के 10 सवाल हैं। यह उन बच्चों के लिए है जो अभी एक-अंक जोड़ सीख रहे हैं। हर सवाल के सही या गलत होने की जानकारी दी जाएगी।\",\n" +
                "        \"language\": [\n" +
                "          \"Hindi\"\n" +
                "        ],\n" +
                "        \"source\": \"EkStep\",\n" +
                "        \"mimeType\": \"application/vnd.ekstep.ecml-archive\",\n" +
                "        \"idealScreenSize\": \"normal\",\n" +
                "        \"createdOn\": \"2016-06-20T06:05:30.986+0000\",\n" +
                "        \"objectType\": \"Content\",\n" +
                "        \"appIcon\": \"https://ekstep-public-qa.s3-ap-south-1.amazonaws.com/language_assets/add-single_1466403353587.png\",\n" +
                "        \"gradeLevel\": [\n" +
                "          \"Grade 1\",\n" +
                "          \"Grade 2\",\n" +
                "          \"Grade 3\",\n" +
                "          \"Grade 4\",\n" +
                "          \"Grade 5\",\n" +
                "          \"Other\"\n" +
                "        ],\n" +
                "        \"artifactUrl\": \"https://ekstep-public-qa.s3-ap-south-1.amazonaws.com/content/1467630762007_do_30013062.zip\",\n" +
                "        \"lastUpdatedOn\": \"2017-05-15T07:03:44.812+0000\",\n" +
                "        \"contentType\": \"Worksheet\",\n" +
                "        \"owner\": \"Parabal Partap Singh\",\n" +
                "        \"lastUpdatedBy\": \"237\",\n" +
                "        \"identifier\": \"do_30013062\",\n" +
                "        \"os\": [\n" +
                "          \"All\"\n" +
                "        ],\n" +
                "        \"visibility\": \"Default\",\n" +
                "        \"soundCredits\": [\n" +
                "          \"ekstep\"\n" +
                "        ],\n" +
                "        \"portalOwner\": \"334\",\n" +
                "        \"mediaType\": \"content\",\n" +
                "        \"osId\": \"org.ekstep.quiz.app\",\n" +
                "        \"ageGroup\": [\n" +
                "          \"5-6\",\n" +
                "          \"6-7\",\n" +
                "          \"7-8\"\n" +
                "        ],\n" +
                "        \"graph_id\": \"domain\",\n" +
                "        \"nodeType\": \"DATA_NODE\",\n" +
                "        \"pkgVersion\": 2.0,\n" +
                "        \"versionKey\": \"1497244069564\",\n" +
                "        \"tags\": [\n" +
                "          \"single digit add\",\n" +
                "          \"jodhna\",\n" +
                "          \"jodh\",\n" +
                "          \"ek ank jodh\",\n" +
                "          \"single digit\",\n" +
                "          \"single digit addition\",\n" +
                "          \"sum\",\n" +
                "          \"addition\",\n" +
                "          \"add\"\n" +
                "        ],\n" +
                "        \"optStatus\": \"Complete\",\n" +
                "        \"idealScreenDensity\": \"hdpi\",\n" +
                "        \"license\": \"Creative Commons Attribution (CC BY)\",\n" +
                "        \"lastPublishedOn\": \"2016-07-04T11:12:43.058+0000\",\n" +
                "        \"size\": 1477174.0,\n" +
                "        \"lastSubmittedOn\": \"2016-07-04T10:09:49.010+0000\",\n" +
                "        \"concepts\": [\n" +
                "          \"Num:C3:SC1:MC18\",\n" +
                "          \"Num:C3:SC1\",\n" +
                "          \"Num:C3:SC1:MC6\"\n" +
                "        ],\n" +
                "        \"domain\": [\n" +
                "          \"numeracy\"\n" +
                "        ],\n" +
                "        \"name\": \"एक-अंक जोड़ (Single Digit Addition)\",\n" +
                "        \"publisher\": \"\",\n" +
                "        \"status\": \"Live\",\n" +
                "        \"node_id\": 12752.0,\n" +
                "        \"me_totalDownloads\": 5.0,\n" +
                "        \"me_averageInteractionsPerMin\": 115.81,\n" +
                "        \"me_totalSessionsCount\": 1.0,\n" +
                "        \"me_totalInteractions\": 94.0,\n" +
                "        \"me_totalSideloads\": 3.0,\n" +
                "        \"me_totalTimespent\": 48.7,\n" +
                "        \"me_averageRating\": 0.0,\n" +
                "        \"me_totalComments\": 0.0,\n" +
                "        \"me_averageTimespentPerSession\": 48.7,\n" +
                "        \"popularity\": 48.7,\n" +
                "        \"me_averageSessionsPerDevice\": 1.0,\n" +
                "        \"me_totalRatings\": 0.0,\n" +
                "        \"me_totalDevices\": 1.0,\n" +
                "        \"compatibilityLevel\": 1.0,\n" +
                "        \"collections\": [\n" +
                "          \n" +
                "        ],\n" +
                "        \"creator\": \"Parabal Partap Singh\",\n" +
                "        \"createdBy\": \"334\",\n" +
                "        \"audience\": [\n" +
                "          \"Learner\"\n" +
                "        ],\n" +
                "        \"consumerId\": \"2c43f136-c02f-4494-9fb9-fd228e2c77e6\",\n" +
                "        \"SYS_INTERNAL_LAST_UPDATED_ON\": \"2017-06-12T05:07:49.564+0000\",\n" +
                "        \"template\": \"\",\n" +
                "        \"s3Key\": \"ecar_files/do_30013062_1467630762635.ecar\",\n" +
                "        \"channel\": \"in.ekstep\",\n" +
                "        \"appId\": \"qa.ekstep.in\",\n" +
                "        \"es_metadata_id\": \"do_30013062\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"copyright\": \"CC0\",\n" +
                "        \"code\": \"org.ekstep.numeracy.worksheet.485\",\n" +
                "        \"imageCredits\": [\n" +
                "          \"ekstep\",\n" +
                "          \"Parabal Singh\",\n" +
                "          \"EkStep\"\n" +
                "        ],\n" +
                "        \"downloadUrl\": \"https://ekstep-public-qa.s3-ap-south-1.amazonaws.com/ecar_files/do_30013147_1467630801490.ecar\",\n" +
                "        \"description\": \"इस वर्कशीट में एक-अंकीय संख्याओं के घटाव के 10 सवाल हैं। यह उन बच्चों के लिए है जो अभी एक-अंक घटाव सीख रहे हैं। हर सवाल के सही या गलत होने की जानकारी दी जाएगी।\",\n" +
                "        \"language\": [\n" +
                "          \"Hindi\"\n" +
                "        ],\n" +
                "        \"source\": \"EkStep\",\n" +
                "        \"mimeType\": \"application/vnd.ekstep.ecml-archive\",\n" +
                "        \"idealScreenSize\": \"normal\",\n" +
                "        \"createdOn\": \"2016-06-20T07:28:41.015+0000\",\n" +
                "        \"objectType\": \"Content\",\n" +
                "        \"appIcon\": \"https://ekstep-public-qa.s3-ap-south-1.amazonaws.com/language_assets/subtract-single_1466409879465.png\",\n" +
                "        \"gradeLevel\": [\n" +
                "          \"Grade 1\",\n" +
                "          \"Grade 2\",\n" +
                "          \"Grade 3\",\n" +
                "          \"Grade 4\",\n" +
                "          \"Grade 5\",\n" +
                "          \"Other\"\n" +
                "        ],\n" +
                "        \"artifactUrl\": \"https://ekstep-public-qa.s3-ap-south-1.amazonaws.com/content/1467630800721_do_30013147.zip\",\n" +
                "        \"lastUpdatedOn\": \"2017-05-15T07:03:47.087+0000\",\n" +
                "        \"contentType\": \"Worksheet\",\n" +
                "        \"owner\": \"Parabal Partap Singh\",\n" +
                "        \"lastUpdatedBy\": \"237\",\n" +
                "        \"identifier\": \"do_30013147\",\n" +
                "        \"os\": [\n" +
                "          \"All\"\n" +
                "        ],\n" +
                "        \"visibility\": \"Default\",\n" +
                "        \"soundCredits\": [\n" +
                "          \"ekstep\"\n" +
                "        ],\n" +
                "        \"portalOwner\": \"334\",\n" +
                "        \"mediaType\": \"content\",\n" +
                "        \"osId\": \"org.ekstep.quiz.app\",\n" +
                "        \"ageGroup\": [\n" +
                "          \"5-6\",\n" +
                "          \"6-7\",\n" +
                "          \"7-8\",\n" +
                "          \"8-10\"\n" +
                "        ],\n" +
                "        \"graph_id\": \"domain\",\n" +
                "        \"nodeType\": \"DATA_NODE\",\n" +
                "        \"pkgVersion\": 2.0,\n" +
                "        \"versionKey\": \"1497244069429\",\n" +
                "        \"tags\": [\n" +
                "          \"difference\",\n" +
                "          \"ghatana\",\n" +
                "          \"subtract\",\n" +
                "          \"single digit subtraction\",\n" +
                "          \"subtraction\",\n" +
                "          \"ghatav\",\n" +
                "          \"ek ank ghatav\"\n" +
                "        ],\n" +
                "        \"optStatus\": \"Complete\",\n" +
                "        \"idealScreenDensity\": \"hdpi\",\n" +
                "        \"license\": \"Creative Commons Attribution (CC BY)\",\n" +
                "        \"lastPublishedOn\": \"2016-07-04T11:13:21.789+0000\",\n" +
                "        \"lastSubmittedOn\": \"2016-07-04T10:09:06.965+0000\",\n" +
                "        \"size\": 1476048.0,\n" +
                "        \"concepts\": [\n" +
                "          \"Num:C3:SC1:MC18\",\n" +
                "          \"Num:C3:SC1:MC6\",\n" +
                "          \"Num:C3:SC1\"\n" +
                "        ],\n" +
                "        \"domain\": [\n" +
                "          \"numeracy\"\n" +
                "        ],\n" +
                "        \"name\": \"एक-अंक घटाव (Single Digit Subtraction)\",\n" +
                "        \"publisher\": \"\",\n" +
                "        \"status\": \"Live\",\n" +
                "        \"node_id\": 12832.0,\n" +
                "        \"compatibilityLevel\": 1.0,\n" +
                "        \"collections\": [\n" +
                "          \"do_2122661848320163841105\"\n" +
                "        ],\n" +
                "        \"me_totalDownloads\": 12.0,\n" +
                "        \"me_averageInteractionsPerMin\": 66.92,\n" +
                "        \"me_totalSessionsCount\": 4.0,\n" +
                "        \"me_totalInteractions\": 59.0,\n" +
                "        \"me_totalSideloads\": 0.0,\n" +
                "        \"me_totalTimespent\": 52.9,\n" +
                "        \"me_totalComments\": 0.0,\n" +
                "        \"me_averageRating\": 4.0,\n" +
                "        \"me_averageTimespentPerSession\": 13.23,\n" +
                "        \"popularity\": 52.9,\n" +
                "        \"me_totalRatings\": 1.0,\n" +
                "        \"me_averageSessionsPerDevice\": 1.33,\n" +
                "        \"me_totalDevices\": 3.0,\n" +
                "        \"creator\": \"Parabal Partap Singh\",\n" +
                "        \"createdBy\": \"334\",\n" +
                "        \"audience\": [\n" +
                "          \"Learner\"\n" +
                "        ],\n" +
                "        \"consumerId\": \"2c43f136-c02f-4494-9fb9-fd228e2c77e6\",\n" +
                "        \"SYS_INTERNAL_LAST_UPDATED_ON\": \"2017-06-12T05:07:49.429+0000\",\n" +
                "        \"template\": \"\",\n" +
                "        \"s3Key\": \"ecar_files/do_30013147_1467630801490.ecar\",\n" +
                "        \"channel\": \"in.ekstep\",\n" +
                "        \"appId\": \"qa.ekstep.in\",\n" +
                "        \"es_metadata_id\": \"do_30013147\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"copyright\": \"CC0\",\n" +
                "        \"downloadUrl\": \"https://ekstep-public-qa.s3-ap-south-1.amazonaws.com/ecar_files/do_30013083_1467630343897.ecar\",\n" +
                "        \"language\": [\n" +
                "          \"Hindi\"\n" +
                "        ],\n" +
                "        \"source\": \"EkStep\",\n" +
                "        \"mimeType\": \"application/vnd.ekstep.ecml-archive\",\n" +
                "        \"objectType\": \"Content\",\n" +
                "        \"appIcon\": \"https://ekstep-public-qa.s3-ap-south-1.amazonaws.com/language_assets/add-double_1466405372117.jpeg\",\n" +
                "        \"gradeLevel\": [\n" +
                "          \"Grade 2\",\n" +
                "          \"Grade 3\",\n" +
                "          \"Grade 4\",\n" +
                "          \"Grade 5\",\n" +
                "          \"Other\"\n" +
                "        ],\n" +
                "        \"me_totalTimespent\": 1820.55,\n" +
                "        \"me_averageTimespentPerSession\": 121.37,\n" +
                "        \"collections\": [\n" +
                "          \n" +
                "        ],\n" +
                "        \"me_totalRatings\": 2.0,\n" +
                "        \"artifactUrl\": \"https://ekstep-public-qa.s3-ap-south-1.amazonaws.com/content/1467630342937_do_30013083.zip\",\n" +
                "        \"contentType\": \"Worksheet\",\n" +
                "        \"lastUpdatedBy\": \"237\",\n" +
                "        \"identifier\": \"do_30013083\",\n" +
                "        \"visibility\": \"Default\",\n" +
                "        \"portalOwner\": \"334\",\n" +
                "        \"mediaType\": \"content\",\n" +
                "        \"osId\": \"org.ekstep.quiz.app\",\n" +
                "        \"ageGroup\": [\n" +
                "          \"5-6\",\n" +
                "          \"6-7\",\n" +
                "          \"7-8\"\n" +
                "        ],\n" +
                "        \"graph_id\": \"domain\",\n" +
                "        \"nodeType\": \"DATA_NODE\",\n" +
                "        \"tags\": [\n" +
                "          \"hasil jodh\",\n" +
                "          \"do ank jodh hasil ke saath\",\n" +
                "          \"do ank jodh\",\n" +
                "          \"double digit addition with carry over\",\n" +
                "          \"double digit addition\",\n" +
                "          \"double digit add\",\n" +
                "          \"hasil ke saath jodh\",\n" +
                "          \"hasil\",\n" +
                "          \"addition with carry over\",\n" +
                "          \"addition with carry\",\n" +
                "          \"jodh\",\n" +
                "          \"add\",\n" +
                "          \"addition\"\n" +
                "        ],\n" +
                "        \"optStatus\": \"Complete\",\n" +
                "        \"license\": \"Creative Commons Attribution (CC BY)\",\n" +
                "        \"lastPublishedOn\": \"2016-07-04T11:05:44.220+0000\",\n" +
                "        \"size\": 1477624.0,\n" +
                "        \"concepts\": [\n" +
                "          \"Num:C3:SC1\",\n" +
                "          \"Num:C3:SC1:MC6\",\n" +
                "          \"Num:C3:SC1:MC20\"\n" +
                "        ],\n" +
                "        \"domain\": [\n" +
                "          \"numeracy\"\n" +
                "        ],\n" +
                "        \"name\": \"दो-अंक जोड़ (हासिल के साथ)\",\n" +
                "        \"me_averageSessionsPerDevice\": 1.67,\n" +
                "        \"publisher\": \"\",\n" +
                "        \"status\": \"Live\",\n" +
                "        \"me_averageInteractionsPerMin\": 12.56,\n" +
                "        \"code\": \"org.ekstep.numeracy.worksheet.473\",\n" +
                "        \"me_totalSessionsCount\": 15.0,\n" +
                "        \"imageCredits\": [\n" +
                "          \"ekstep\",\n" +
                "          \"Parabal Singh\",\n" +
                "          \"EkStep\"\n" +
                "        ],\n" +
                "        \"description\": \"इस वर्कशीट में दो-अंकीय संख्याओं के हासिल के साथ जोड़ के 10 सवाल हैं। यह उन बच्चों के लिए है जो अभी हासिल के साथ जोड़ सीख रहे हैं। हर सवाल के सही या गलत होने की जानकारी दी जाएगी।\",\n" +
                "        \"idealScreenSize\": \"normal\",\n" +
                "        \"createdOn\": \"2016-06-20T06:25:03.883+0000\",\n" +
                "        \"me_totalSideloads\": 0.0,\n" +
                "        \"me_totalComments\": 0.0,\n" +
                "        \"popularity\": 1820.55,\n" +
                "        \"lastUpdatedOn\": \"2017-05-15T07:03:44.687+0000\",\n" +
                "        \"me_totalDevices\": 9.0,\n" +
                "        \"me_totalDownloads\": 24.0,\n" +
                "        \"owner\": \"Parabal Partap Singh\",\n" +
                "        \"os\": [\n" +
                "          \"All\"\n" +
                "        ],\n" +
                "        \"soundCredits\": [\n" +
                "          \"ekstep\"\n" +
                "        ],\n" +
                "        \"me_totalInteractions\": 381.0,\n" +
                "        \"pkgVersion\": 2.0,\n" +
                "        \"versionKey\": \"1497244078041\",\n" +
                "        \"idealScreenDensity\": \"hdpi\",\n" +
                "        \"lastSubmittedOn\": \"2016-07-04T10:13:23.774+0000\",\n" +
                "        \"me_averageRating\": 5.0,\n" +
                "        \"node_id\": 12770.0,\n" +
                "        \"compatibilityLevel\": 1.0,\n" +
                "        \"creator\": \"Parabal Partap Singh\",\n" +
                "        \"createdBy\": \"334\",\n" +
                "        \"audience\": [\n" +
                "          \"Learner\"\n" +
                "        ],\n" +
                "        \"consumerId\": \"2c43f136-c02f-4494-9fb9-fd228e2c77e6\",\n" +
                "        \"SYS_INTERNAL_LAST_UPDATED_ON\": \"2017-06-12T05:07:58.041+0000\",\n" +
                "        \"template\": \"\",\n" +
                "        \"s3Key\": \"ecar_files/do_30013083_1467630343897.ecar\",\n" +
                "        \"channel\": \"in.ekstep\",\n" +
                "        \"appId\": \"qa.ekstep.in\",\n" +
                "        \"es_metadata_id\": \"do_30013083\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"copyright\": \"CC0\",\n" +
                "        \"code\": \"org.ekstep.numeracy.worksheet.486\",\n" +
                "        \"imageCredits\": [\n" +
                "          \"ekstep\",\n" +
                "          \"Parabal Singh\",\n" +
                "          \"EkStep\"\n" +
                "        ],\n" +
                "        \"downloadUrl\": \"https://ekstep-public-qa.s3-ap-south-1.amazonaws.com/ecar_files/do_30013148_1467630377581.ecar\",\n" +
                "        \"description\": \"इस वर्कशीट में दो-अंकीय संख्याओं के बिना उधार घटाव के 10 सवाल हैं। यह उन बच्चों के लिए है जो अभी दो-अंक घटाव सीख रहे हैं। हर सवाल के सही या गलत होने की जानकारी दी जाएगी।\",\n" +
                "        \"language\": [\n" +
                "          \"Hindi\"\n" +
                "        ],\n" +
                "        \"source\": \"EkStep\",\n" +
                "        \"mimeType\": \"application/vnd.ekstep.ecml-archive\",\n" +
                "        \"idealScreenSize\": \"normal\",\n" +
                "        \"createdOn\": \"2016-06-20T07:29:54.404+0000\",\n" +
                "        \"objectType\": \"Content\",\n" +
                "        \"appIcon\": \"https://ekstep-public-qa.s3-ap-south-1.amazonaws.com/language_assets/subtract-double_1466412197579.png\",\n" +
                "        \"gradeLevel\": [\n" +
                "          \"Grade 2\",\n" +
                "          \"Grade 3\",\n" +
                "          \"Grade 4\",\n" +
                "          \"Grade 5\",\n" +
                "          \"Other\"\n" +
                "        ],\n" +
                "        \"artifactUrl\": \"https://ekstep-public-qa.s3-ap-south-1.amazonaws.com/content/1467630376968_do_30013148.zip\",\n" +
                "        \"lastUpdatedOn\": \"2017-05-15T07:03:47.697+0000\",\n" +
                "        \"contentType\": \"Worksheet\",\n" +
                "        \"owner\": \"Parabal Partap Singh\",\n" +
                "        \"lastUpdatedBy\": \"237\",\n" +
                "        \"identifier\": \"do_30013148\",\n" +
                "        \"os\": [\n" +
                "          \"All\"\n" +
                "        ],\n" +
                "        \"visibility\": \"Default\",\n" +
                "        \"soundCredits\": [\n" +
                "          \"ekstep\"\n" +
                "        ],\n" +
                "        \"portalOwner\": \"334\",\n" +
                "        \"mediaType\": \"content\",\n" +
                "        \"osId\": \"org.ekstep.quiz.app\",\n" +
                "        \"ageGroup\": [\n" +
                "          \"5-6\",\n" +
                "          \"6-7\",\n" +
                "          \"7-8\"\n" +
                "        ],\n" +
                "        \"graph_id\": \"domain\",\n" +
                "        \"nodeType\": \"DATA_NODE\",\n" +
                "        \"pkgVersion\": 2.0,\n" +
                "        \"versionKey\": \"1497244078176\",\n" +
                "        \"tags\": [\n" +
                "          \"do ank ghatav bina udhar\",\n" +
                "          \"bina udhar\",\n" +
                "          \"do ank ghatav\",\n" +
                "          \"ghatav\",\n" +
                "          \"bina udhar ke ghatav\",\n" +
                "          \"double digit subtraction\",\n" +
                "          \"difference\",\n" +
                "          \"subtraction\",\n" +
                "          \"subtract\",\n" +
                "          \"subtract without borrowing\",\n" +
                "          \"subtraction without borrowing\",\n" +
                "          \"double digit subtraction without borrowing\",\n" +
                "          \"double digit subtract\"\n" +
                "        ],\n" +
                "        \"optStatus\": \"Complete\",\n" +
                "        \"idealScreenDensity\": \"hdpi\",\n" +
                "        \"license\": \"Creative Commons Attribution (CC BY)\",\n" +
                "        \"lastPublishedOn\": \"2016-07-04T11:06:17.886+0000\",\n" +
                "        \"lastSubmittedOn\": \"2016-07-04T10:12:34.195+0000\",\n" +
                "        \"size\": 1476852.0,\n" +
                "        \"concepts\": [\n" +
                "          \"Num:C3:SC1\",\n" +
                "          \"Num:C3:SC1:MC6\",\n" +
                "          \"Num:C3:SC1:MC20\"\n" +
                "        ],\n" +
                "        \"domain\": [\n" +
                "          \"numeracy\"\n" +
                "        ],\n" +
                "        \"name\": \"दो-अंक घटाव (बिना उधार)\",\n" +
                "        \"publisher\": \"\",\n" +
                "        \"status\": \"Live\",\n" +
                "        \"node_id\": 12833.0,\n" +
                "        \"compatibilityLevel\": 1.0,\n" +
                "        \"collections\": [\n" +
                "          \"do_2122661848320163841105\"\n" +
                "        ],\n" +
                "        \"me_totalDownloads\": 10.0,\n" +
                "        \"me_averageInteractionsPerMin\": 77.75,\n" +
                "        \"me_totalSessionsCount\": 3.0,\n" +
                "        \"me_totalInteractions\": 52.0,\n" +
                "        \"me_totalSideloads\": 0.0,\n" +
                "        \"me_totalTimespent\": 40.13,\n" +
                "        \"me_totalComments\": 0.0,\n" +
                "        \"me_averageRating\": 5.0,\n" +
                "        \"me_averageTimespentPerSession\": 13.38,\n" +
                "        \"popularity\": 40.13,\n" +
                "        \"me_totalRatings\": 1.0,\n" +
                "        \"me_averageSessionsPerDevice\": 1.0,\n" +
                "        \"me_totalDevices\": 3.0,\n" +
                "        \"creator\": \"Parabal Partap Singh\",\n" +
                "        \"createdBy\": \"334\",\n" +
                "        \"audience\": [\n" +
                "          \"Learner\"\n" +
                "        ],\n" +
                "        \"consumerId\": \"2c43f136-c02f-4494-9fb9-fd228e2c77e6\",\n" +
                "        \"SYS_INTERNAL_LAST_UPDATED_ON\": \"2017-06-12T05:07:58.176+0000\",\n" +
                "        \"template\": \"\",\n" +
                "        \"s3Key\": \"ecar_files/do_30013148_1467630377581.ecar\",\n" +
                "        \"channel\": \"in.ekstep\",\n" +
                "        \"appId\": \"qa.ekstep.in\",\n" +
                "        \"es_metadata_id\": \"do_30013148\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"facets\": [\n" +
                "      {\n" +
                "        \"values\": [\n" +
                "          {\n" +
                "            \"name\": \"other\",\n" +
                "            \"count\": 27\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"kindergarten\",\n" +
                "            \"count\": 4\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"grade 1\",\n" +
                "            \"count\": 28\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"grade 2\",\n" +
                "            \"count\": 30\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"grade 3\",\n" +
                "            \"count\": 29\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"grade 4\",\n" +
                "            \"count\": 29\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"grade 5\",\n" +
                "            \"count\": 29\n" +
                "          }\n" +
                "        ],\n" +
                "        \"name\": \"gradeLevel\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"values\": [\n" +
                "          {\n" +
                "            \"name\": \"literacy\",\n" +
                "            \"count\": 5\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"numeracy\",\n" +
                "            \"count\": 28\n" +
                "          }\n" +
                "        ],\n" +
                "        \"name\": \"domain\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"values\": [\n" +
                "          {\n" +
                "            \"name\": \"english\",\n" +
                "            \"count\": 22\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"hindi\",\n" +
                "            \"count\": 10\n" +
                "          }\n" +
                "        ],\n" +
                "        \"name\": \"language\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"values\": [\n" +
                "          {\n" +
                "            \"name\": \"6-7\",\n" +
                "            \"count\": 31\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"7-8\",\n" +
                "            \"count\": 30\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"5-6\",\n" +
                "            \"count\": 32\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"8-10\",\n" +
                "            \"count\": 14\n" +
                "          }\n" +
                "        ],\n" +
                "        \"name\": \"ageGroup\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"values\": [\n" +
                "          {\n" +
                "            \"name\": \"worksheet\",\n" +
                "            \"count\": 27\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"collection\",\n" +
                "            \"count\": 2\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"story\",\n" +
                "            \"count\": 3\n" +
                "          }\n" +
                "        ],\n" +
                "        \"name\": \"contentType\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}";
    }

    public static String responseForContentDownload() {
        return "{\n" +
                "  \"id\": \"ekstep.composite-search.search\",\n" +
                "  \"ver\": \"3.0\",\n" +
                "  \"ts\": \"2017-07-28T05:29:13ZZ\",\n" +
                "  \"params\": {\n" +
                "    \"resmsgid\": \"ff2ce054-5fec-4579-b14f-fa452f5eb525\",\n" +
                "    \"msgid\": null,\n" +
                "    \"err\": null,\n" +
                "    \"status\": \"successful\",\n" +
                "    \"errmsg\": null\n" +
                "  },\n" +
                "  \"responseCode\": \"OK\",\n" +
                "  \"result\": {\n" +
                "    \"count\": 1,\n" +
                "    \"content\": [\n" +
                "      {\n" +
                "        \"copyright\": \"CC0\",\n" +
                "        \"code\": \"org.ekstep.numeracy.worksheet.471\",\n" +
                "        \"imageCredits\": [\n" +
                "          \"ekstep\",\n" +
                "          \"Parabal Singh\",\n" +
                "          \"EkStep\"\n" +
                "        ],\n" +
                "        \"downloadUrl\": \"https://ekstep-public-qa.s3-ap-south-1.amazonaws.com/ecar_files/do_30013062_1467630762635.ecar\",\n" +
                "        \"description\": \"इस वर्कशीट में एक-अंकीय संख्याओं के जोड़ के 10 सवाल हैं। यह उन बच्चों के लिए है जो अभी एक-अंक जोड़ सीख रहे हैं। हर सवाल के सही या गलत होने की जानकारी दी जाएगी।\",\n" +
                "        \"language\": [\n" +
                "          \"Hindi\"\n" +
                "        ],\n" +
                "        \"source\": \"EkStep\",\n" +
                "        \"mimeType\": \"application/vnd.ekstep.ecml-archive\",\n" +
                "        \"idealScreenSize\": \"normal\",\n" +
                "        \"createdOn\": \"2016-06-20T06:05:30.986+0000\",\n" +
                "        \"objectType\": \"Content\",\n" +
                "        \"appIcon\": \"https://ekstep-public-qa.s3-ap-south-1.amazonaws.com/language_assets/add-single_1466403353587.png\",\n" +
                "        \"gradeLevel\": [\n" +
                "          \"Grade 1\",\n" +
                "          \"Grade 2\",\n" +
                "          \"Grade 3\",\n" +
                "          \"Grade 4\",\n" +
                "          \"Grade 5\",\n" +
                "          \"Other\"\n" +
                "        ],\n" +
                "        \"artifactUrl\": \"https://ekstep-public-qa.s3-ap-south-1.amazonaws.com/content/1467630762007_do_30013062.zip\",\n" +
                "        \"lastUpdatedOn\": \"2017-05-15T07:03:44.812+0000\",\n" +
                "        \"contentType\": \"Worksheet\",\n" +
                "        \"owner\": \"Parabal Partap Singh\",\n" +
                "        \"lastUpdatedBy\": \"237\",\n" +
                "        \"identifier\": \"do_30013062\",\n" +
                "        \"os\": [\n" +
                "          \"All\"\n" +
                "        ],\n" +
                "        \"visibility\": \"Default\",\n" +
                "        \"soundCredits\": [\n" +
                "          \"ekstep\"\n" +
                "        ],\n" +
                "        \"portalOwner\": \"334\",\n" +
                "        \"mediaType\": \"content\",\n" +
                "        \"osId\": \"org.ekstep.quiz.app\",\n" +
                "        \"ageGroup\": [\n" +
                "          \"5-6\",\n" +
                "          \"6-7\",\n" +
                "          \"7-8\"\n" +
                "        ],\n" +
                "        \"graph_id\": \"domain\",\n" +
                "        \"nodeType\": \"DATA_NODE\",\n" +
                "        \"pkgVersion\": 2.0,\n" +
                "        \"versionKey\": \"1497244069564\",\n" +
                "        \"tags\": [\n" +
                "          \"single digit add\",\n" +
                "          \"jodhna\",\n" +
                "          \"jodh\",\n" +
                "          \"ek ank jodh\",\n" +
                "          \"single digit\",\n" +
                "          \"single digit addition\",\n" +
                "          \"sum\",\n" +
                "          \"addition\",\n" +
                "          \"add\"\n" +
                "        ],\n" +
                "        \"optStatus\": \"Complete\",\n" +
                "        \"idealScreenDensity\": \"hdpi\",\n" +
                "        \"license\": \"Creative Commons Attribution (CC BY)\",\n" +
                "        \"lastPublishedOn\": \"2016-07-04T11:12:43.058+0000\",\n" +
                "        \"size\": 1477174.0,\n" +
                "        \"lastSubmittedOn\": \"2016-07-04T10:09:49.010+0000\",\n" +
                "        \"concepts\": [\n" +
                "          \"Num:C3:SC1:MC18\",\n" +
                "          \"Num:C3:SC1\",\n" +
                "          \"Num:C3:SC1:MC6\"\n" +
                "        ],\n" +
                "        \"domain\": [\n" +
                "          \"numeracy\"\n" +
                "        ],\n" +
                "        \"name\": \"एक-अंक जोड़ (Single Digit Addition)\",\n" +
                "        \"publisher\": \"\",\n" +
                "        \"status\": \"Live\",\n" +
                "        \"node_id\": 12752.0,\n" +
                "        \"me_totalDownloads\": 5.0,\n" +
                "        \"me_averageInteractionsPerMin\": 115.81,\n" +
                "        \"me_totalSessionsCount\": 1.0,\n" +
                "        \"me_totalInteractions\": 94.0,\n" +
                "        \"me_totalSideloads\": 3.0,\n" +
                "        \"me_totalTimespent\": 48.7,\n" +
                "        \"me_averageRating\": 0.0,\n" +
                "        \"me_totalComments\": 0.0,\n" +
                "        \"me_averageTimespentPerSession\": 48.7,\n" +
                "        \"popularity\": 48.7,\n" +
                "        \"me_averageSessionsPerDevice\": 1.0,\n" +
                "        \"me_totalRatings\": 0.0,\n" +
                "        \"me_totalDevices\": 1.0,\n" +
                "        \"compatibilityLevel\": 1.0,\n" +
                "        \"collections\": [\n" +
                "          \n" +
                "        ],\n" +
                "        \"creator\": \"Parabal Partap Singh\",\n" +
                "        \"createdBy\": \"334\",\n" +
                "        \"audience\": [\n" +
                "          \"Learner\"\n" +
                "        ],\n" +
                "        \"consumerId\": \"2c43f136-c02f-4494-9fb9-fd228e2c77e6\",\n" +
                "        \"SYS_INTERNAL_LAST_UPDATED_ON\": \"2017-06-12T05:07:49.564+0000\",\n" +
                "        \"template\": \"\",\n" +
                "        \"s3Key\": \"ecar_files/do_30013062_1467630762635.ecar\",\n" +
                "        \"channel\": \"in.ekstep\",\n" +
                "        \"appId\": \"qa.ekstep.in\",\n" +
                "        \"es_metadata_id\": \"do_30013062\"\n" +
                "      }" +
                "    ],\n" +
                "    \"facets\": [\n" +
                "      {\n" +
                "        \"values\": [\n" +
                "          {\n" +
                "            \"name\": \"other\",\n" +
                "            \"count\": 27\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"kindergarten\",\n" +
                "            \"count\": 4\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"grade 1\",\n" +
                "            \"count\": 28\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"grade 2\",\n" +
                "            \"count\": 30\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"grade 3\",\n" +
                "            \"count\": 29\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"grade 4\",\n" +
                "            \"count\": 29\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"grade 5\",\n" +
                "            \"count\": 29\n" +
                "          }\n" +
                "        ],\n" +
                "        \"name\": \"gradeLevel\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"values\": [\n" +
                "          {\n" +
                "            \"name\": \"literacy\",\n" +
                "            \"count\": 5\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"numeracy\",\n" +
                "            \"count\": 28\n" +
                "          }\n" +
                "        ],\n" +
                "        \"name\": \"domain\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"values\": [\n" +
                "          {\n" +
                "            \"name\": \"english\",\n" +
                "            \"count\": 22\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"hindi\",\n" +
                "            \"count\": 10\n" +
                "          }\n" +
                "        ],\n" +
                "        \"name\": \"language\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"values\": [\n" +
                "          {\n" +
                "            \"name\": \"6-7\",\n" +
                "            \"count\": 31\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"7-8\",\n" +
                "            \"count\": 30\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"5-6\",\n" +
                "            \"count\": 32\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"8-10\",\n" +
                "            \"count\": 14\n" +
                "          }\n" +
                "        ],\n" +
                "        \"name\": \"ageGroup\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"values\": [\n" +
                "          {\n" +
                "            \"name\": \"worksheet\",\n" +
                "            \"count\": 27\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"collection\",\n" +
                "            \"count\": 2\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"story\",\n" +
                "            \"count\": 3\n" +
                "          }\n" +
                "        ],\n" +
                "        \"name\": \"contentType\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}";
    }


    public static String filterResponse(int filterCode) {
        String response;

        switch (filterCode) {
            case 1:
                response = contentSearchResponse();
                break;

            case 2:
                response = searchResponse();
                break;

            default:
                response = getRecommendedContentResponse();
        }

        return response;
    }

    public static String contentSearchResponse() {
        return "{\n" +
                "  \"id\": \"ekstep.composite-search.search\",\n" +
                "  \"ver\": \"1.0\",\n" +
                "  \"ts\": \"2016-05-04T11:49:00ZZ\",\n" +
                "  \"params\": {\n" +
                "    \"resmsgid\": \"4fdb3f58-9667-45a8-a178-f11a02892e82\",\n" +
                "    \"msgid\": null,\n" +
                "    \"err\": null,\n" +
                "    \"status\": \"Success\",\n" +
                "    \"errmsg\": null\n" +
                "  },\n" +
                "  \"responseCode\": \"OK\",\n" +
                "  \"result\": {\n" +
                "    \"content\": [\n" +
                "      {\n" +
                "        \"genre\": [\n" +
                "          \"Picture Books\"\n" +
                "        ],\n" +
                "        \"appIcon\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/language_assets/1452170713095-logo_1459226378582.png\",\n" +
                "        \"idealScreenSize\": \"normal\",\n" +
                "        \"visibility\": \"Default\",\n" +
                "        \"downloadUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/domain_14433_1459343205901.ecar\",\n" +
                "        \"contentType\": \"Story\",\n" +
                "        \"mediaType\": \"content\",\n" +
                "        \"osId\": \"org.ekstep.quiz.app\",\n" +
                "        \"graph_id\": \"domain\",\n" +
                "        \"description\": \"I am trying to see if I can successfully translate one story from English to Hindi\",\n" +
                "        \"name\": \" How to Weigh an elephant in Hindi?\",\n" +
                "        \"nodeType\": \"DATA_NODE\",\n" +
                "        \"mimeType\": \"application/vnd.ekstep.ecml-archive\",\n" +
                "        \"pkgVersion\": 2,\n" +
                "        \"ageGroup\": [\n" +
                "          \"<5\"\n" +
                "        ],\n" +
                "        \"os\": [\n" +
                "          \"All\"\n" +
                "        ],\n" +
                "        \"idealScreenDensity\": \"hdpi\",\n" +
                "        \"status\": \"Live\",\n" +
                "        \"code\": \"org.ekstep.literacy.story.172\",\n" +
                "        \"developer\": \"EkStep\",\n" +
                "        \"owner\": \"275\",\n" +
                "        \"gradeLevel\": [\n" +
                "          \"Grade 1\"\n" +
                "        ],\n" +
                "        \"artifactUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/1455559521178-org-ekstep-howtoweighanelephant_1459226577470.zip\",\n" +
                "        \"language\": [\n" +
                "          \"Hindi\"\n" +
                "        ],\n" +
                "        \"portalOwner\": \"EkStep\",\n" +
                "        \"objectType\": \"Content\",\n" +
                "        \"identifier\": \"domain_14433\",\n" +
                "        \"node_id\": 14433,\n" +
                "        \"publisher\": \"EkStep\",\n" +
                "        \"license\": \"Creative Commons Attribution (CC BY)\",\n" +
                "        \"popularity\": 1,\n" +
                "        \"source\": \"EkStep\",\n" +
                "        \"es_metadata_id\": \"domain_14433\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"os\": [\n" +
                "          \"All\"\n" +
                "        ],\n" +
                "        \"idealScreenDensity\": \"hdpi\",\n" +
                "        \"idealScreenSize\": \"normal\",\n" +
                "        \"visibility\": \"Default\",\n" +
                "        \"status\": \"Live\",\n" +
                "        \"downloadUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/1455036372377Elephant.png\",\n" +
                "        \"code\": \"elephant\",\n" +
                "        \"contentType\": \"Asset\",\n" +
                "        \"mediaType\": \"image\",\n" +
                "        \"graph_id\": \"domain\",\n" +
                "        \"developer\": \"EkStep\",\n" +
                "        \"name\": \"elephant\",\n" +
                "        \"owner\": \"ekstep\",\n" +
                "        \"gradeLevel\": [\n" +
                "          \"Grade 1\"\n" +
                "        ],\n" +
                "        \"language\": [\n" +
                "          \"English\"\n" +
                "        ],\n" +
                "        \"nodeType\": \"DATA_NODE\",\n" +
                "        \"identifier\": \"elephant\",\n" +
                "        \"objectType\": \"Content\",\n" +
                "        \"mimeType\": \"application/octet-stream\",\n" +
                "        \"pkgVersion\": 1,\n" +
                "        \"ageGroup\": [\n" +
                "          \"5-6\"\n" +
                "        ],\n" +
                "        \"node_id\": 4406,\n" +
                "        \"es_metadata_id\": \"elephant\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"facets\": [\n" +
                "      {\n" +
                "        \"values\": [],\n" +
                "        \"name\": \"domain\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"values\": [\n" +
                "          {\n" +
                "            \"count\": 1,\n" +
                "            \"name\": \"grade 3\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"count\": 2,\n" +
                "            \"name\": \"grade 2\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"count\": 731,\n" +
                "            \"name\": \"grade 1\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"count\": 1,\n" +
                "            \"name\": \"kindergarten\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"name\": \"gradeLevel\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"values\": [\n" +
                "          {\n" +
                "            \"count\": 3,\n" +
                "            \"name\": \"hindi\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"count\": 728,\n" +
                "            \"name\": \"english\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"count\": 1,\n" +
                "            \"name\": \"kannada\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"name\": \"language\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"values\": [\n" +
                "          {\n" +
                "            \"count\": 9,\n" +
                "            \"name\": \"story\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"count\": 723,\n" +
                "            \"name\": \"asset\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"name\": \"contentType\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"values\": [\n" +
                "          {\n" +
                "            \"count\": 2,\n" +
                "            \"name\": \"<5\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"count\": 729,\n" +
                "            \"name\": \"5-6\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"count\": 1,\n" +
                "            \"name\": \"8-10\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"count\": 1,\n" +
                "            \"name\": \"7-8\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"count\": 2,\n" +
                "            \"name\": \"6-7\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"name\": \"ageGroup\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}";
    }

    public static String getContentResponse(String content_identifier) {
        return "{\n" +
                "  \"id\": \"ekstep.content.info\",\n" +
                "  \"ver\": \"2.0\",\n" +
                "  \"ts\": \"2016-05-02T11:45:00ZZ\",\n" +
                "  \"params\": {\n" +
                "    \"resmsgid\": \"4f5fa80a-9ee4-4003-8cbe-826842b16dbe\",\n" +
                "    \"msgid\": null,\n" +
                "    \"err\": null,\n" +
                "    \"status\": \"successful\",\n" +
                "    \"errmsg\": null\n" +
                "  },\n" +
                "  \"responseCode\": \"OK\",\n" +
                "  \"result\": {\n" +
                "    \"content\": {\n" +
                "      \"tags\": [\n" +
                "        \"elephant\"\n" +
                "      ],\n" +
                "      \"appIcon\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/language_assets/cutmypic_1459156399517.png\",\n" +
                "      \"visibility\": \"Default\",\n" +
                "      \"status\": \"Live\",\n" +
                "      \"downloadUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/domain_14302_1459396142038.ecar\",\n" +
                "      \"contentType\": \"Story\",\n" +
                "      \"code\": \"org.ekstep.ordinal.story\",\n" +
                "      \"lastUpdatedOn\": \"2016-04-28T14:35:18.701+0000\",\n" +
                "      \"concepts\": [\n" +
                "        {\n" +
                "          \"identifier\": \"Num:C1:SC2:MC12\",\n" +
                "          \"name\": \"Make shapes using Tangrams; tile an area using shapes\",\n" +
                "          \"objectType\": \"Concept\",\n" +
                "          \"relation\": \"associatedTo\",\n" +
                "          \"description\": \"Make shapes using Tangrams; tile an area using shapes\",\n" +
                "          \"index\": null\n" +
                "        }\n" +
                "      ],\n" +
                "      \"mediaType\": \"content\",\n" +
                "      \"createdOn\": \"2016-03-28T09:13:19.470+0000\",\n" +
                "      \"osId\": \"org.ekstep.quiz.app\",\n" +
                "      \"languageCode\": \"en\",\n" +
                "      \"developer\": \"EkStep\",\n" +
                "      \"description\": \"Elephant and the Monkey\",\n" +
                "      \"name\": \"Elephant and the Monkey\",\n" +
                "      \"owner\": \"EkStep\",\n" +
                "      \"artifactUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/1459396141532_domain_14302.zip\",\n" +
                "      \"language\": [\n" +
                "        \"English\"\n" +
                "      ],\n" +
                "      \"identifier\": \"" + content_identifier + "\",\n" +
                "      \"portalOwner\": \"EkStep\",\n" +
                "      \"collections\": [\n" +
                "        {\n" +
                "          \"identifier\": \"domain_38926\",\n" +
                "          \"name\": \"Elephant Stories\",\n" +
                "          \"objectType\": \"Content\",\n" +
                "          \"relation\": \"hasSequenceMember\",\n" +
                "          \"description\": \"Elephant Stories\",\n" +
                "          \"index\": null\n" +
                "        }\n" +
                "      ],\n" +
                "      \"mimeType\": \"application/vnd.ekstep.ecml-archive\",\n" +
                "      \"pkgVersion\": 1\n" +
                "    }\n" +
                "  }\n" +
                "}";
    }

    public static String getLiveContentDetailsResponse(String content_identifier) {
        return "{\n" +
                "  \"id\": \"ekstep.content.info\",\n" +
                "  \"ver\": \"2.0\",\n" +
                "  \"ts\": \"2016-05-02T11:45:00ZZ\",\n" +
                "  \"params\": {\n" +
                "    \"resmsgid\": \"4f5fa80a-9ee4-4003-8cbe-826842b16dbe\",\n" +
                "    \"msgid\": null,\n" +
                "    \"err\": null,\n" +
                "    \"status\": \"successful\",\n" +
                "    \"errmsg\": null\n" +
                "  },\n" +
                "  \"responseCode\": \"OK\",\n" +
                "  \"result\": {\n" +
                "    \"content\": {\n" +
                "      \"tags\": [\n" +
                "        \"elephant\"\n" +
                "      ],\n" +
                "      \"appIcon\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/language_assets/cutmypic_1459156399517.png\",\n" +
                "      \"visibility\": \"Default\",\n" +
                "      \"status\": \"Live\",\n" +
                "      \"downloadUrl\": \"https://ekstep-public-qa.s3-ap-south-1.amazonaws.com/ecar_files/do_30013486_1467630893935.ecar\",\n" +
                "      \"contentType\": \"Story\",\n" +
                "      \"code\": \"org.ekstep.ordinal.story\",\n" +
                "      \"lastUpdatedOn\": \"2016-04-28T14:35:18.701+0000\",\n" +
                "      \"concepts\": [\n" +
                "        {\n" +
                "          \"identifier\": \"Num:C1:SC2:MC12\",\n" +
                "          \"name\": \"Make shapes using Tangrams; tile an area using shapes\",\n" +
                "          \"objectType\": \"Concept\",\n" +
                "          \"relation\": \"associatedTo\",\n" +
                "          \"description\": \"Make shapes using Tangrams; tile an area using shapes\",\n" +
                "          \"index\": null\n" +
                "        }\n" +
                "      ],\n" +
                "      \"mediaType\": \"content\",\n" +
                "      \"createdOn\": \"2016-03-28T09:13:19.470+0000\",\n" +
                "      \"osId\": \"org.ekstep.quiz.app\",\n" +
                "      \"languageCode\": \"en\",\n" +
                "      \"developer\": \"EkStep\",\n" +
                "      \"description\": \"Elephant and the Monkey\",\n" +
                "      \"name\": \"Elephant and the Monkey\",\n" +
                "      \"owner\": \"EkStep\",\n" +
                "      \"artifactUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/1459396141532_domain_14302.zip\",\n" +
                "      \"language\": [\n" +
                "        \"English\"\n" +
                "      ],\n" +
                "      \"identifier\": \"" + content_identifier + "\",\n" +
                "      \"portalOwner\": \"EkStep\",\n" +
                "      \"collections\": [\n" +
                "        {\n" +
                "          \"identifier\": \"domain_38926\",\n" +
                "          \"name\": \"Elephant Stories\",\n" +
                "          \"objectType\": \"Content\",\n" +
                "          \"relation\": \"hasSequenceMember\",\n" +
                "          \"description\": \"Elephant Stories\",\n" +
                "          \"index\": null\n" +
                "        }\n" +
                "      ],\n" +
                "      \"mimeType\": \"application/vnd.ekstep.ecml-archive\",\n" +
                "      \"pkgVersion\": 1\n" +
                "    }\n" +
                "  }\n" +
                "}";
    }

    public static String getContentResponseWithHugeEcar(String content_identifier) {
        return "{\n" +
                "  \"id\": \"ekstep.content.info\",\n" +
                "  \"ver\": \"2.0\",\n" +
                "  \"ts\": \"2016-05-02T11:45:00ZZ\",\n" +
                "  \"params\": {\n" +
                "    \"resmsgid\": \"4f5fa80a-9ee4-4003-8cbe-826842b16dbe\",\n" +
                "    \"msgid\": null,\n" +
                "    \"err\": null,\n" +
                "    \"status\": \"successful\",\n" +
                "    \"errmsg\": null\n" +
                "  },\n" +
                "  \"responseCode\": \"OK\",\n" +
                "  \"result\": {\n" +
                "    \"content\": {\n" +
                "      \"tags\": [\n" +
                "        \"elephant\"\n" +
                "      ],\n" +
                "      \"appIcon\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/language_assets/cutmypic_1459156399517.png\",\n" +
                "      \"visibility\": \"Default\",\n" +
                "      \"status\": \"Live\",\n" +
                "      \"downloadUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/do_30022230_1467196236400.ecar\",\n" +
                "      \"contentType\": \"Story\",\n" +
                "      \"code\": \"org.ekstep.ordinal.story\",\n" +
                "      \"lastUpdatedOn\": \"2016-04-28T14:35:18.701+0000\",\n" +
                "      \"concepts\": [\n" +
                "        {\n" +
                "          \"identifier\": \"Num:C1:SC2:MC12\",\n" +
                "          \"name\": \"Make shapes using Tangrams; tile an area using shapes\",\n" +
                "          \"objectType\": \"Concept\",\n" +
                "          \"relation\": \"associatedTo\",\n" +
                "          \"description\": \"Make shapes using Tangrams; tile an area using shapes\",\n" +
                "          \"index\": null\n" +
                "        }\n" +
                "      ],\n" +
                "      \"mediaType\": \"content\",\n" +
                "      \"createdOn\": \"2016-03-28T09:13:19.470+0000\",\n" +
                "      \"osId\": \"org.ekstep.quiz.app\",\n" +
                "      \"languageCode\": \"en\",\n" +
                "      \"developer\": \"EkStep\",\n" +
                "      \"description\": \"Elephant and the Monkey\",\n" +
                "      \"name\": \"Elephant and the Monkey\",\n" +
                "      \"owner\": \"EkStep\",\n" +
                "      \"artifactUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/1459396141532_domain_14302.zip\",\n" +
                "      \"language\": [\n" +
                "        \"English\"\n" +
                "      ],\n" +
                "      \"identifier\": \"" + content_identifier + "\",\n" +
                "      \"portalOwner\": \"EkStep\",\n" +
                "      \"collections\": [\n" +
                "        {\n" +
                "          \"identifier\": \"domain_38926\",\n" +
                "          \"name\": \"Elephant Stories\",\n" +
                "          \"objectType\": \"Content\",\n" +
                "          \"relation\": \"hasSequenceMember\",\n" +
                "          \"description\": \"Elephant Stories\",\n" +
                "          \"index\": null\n" +
                "        }\n" +
                "      ],\n" +
                "      \"mimeType\": \"application/vnd.ekstep.ecml-archive\",\n" +
                "      \"pkgVersion\": 1\n" +
                "    }\n" +
                "  }\n" +
                "}";
    }

    public static String emptyGetContentResponse() {
        return "{\n" +
                "  \"id\": \"ekstep.content.info\",\n" +
                "  \"ver\": \"2.0\",\n" +
                "  \"ts\": \"2016-05-04T07:41:21ZZ\",\n" +
                "  \"params\": {\n" +
                "    \"resmsgid\": \"e72cd909-7430-48ad-93fe-b04639cdd2fd\",\n" +
                "    \"msgid\": null,\n" +
                "    \"err\": \"ERR_GRAPH_SEARCH_UNKNOWN_ERROR\",\n" +
                "    \"status\": \"failed\",\n" +
                "    \"errmsg\": \"com.ilimi.common.exception.ResourceNotFoundException: Node not found: domain_143\"\n" +
                "  },\n" +
                "  \"responseCode\": \"RESOURCE_NOT_FOUND\",\n" +
                "  \"result\": {}\n" +
                "}";
    }

    public static String getGeFeedbackEvent() {
        return "{\n" +
                "\"did\":\"517115868495bee6120045db8c1c4a2181156508\",\n" +
                "\"edata\":{\n" +
                "\"eks\":{\n" +
                "\"type\":\"RATING\",\n" +
                "\"rating\":4,\n" +
                "\"context\":{\n" +
                "\"src\":\"Content\",\n" +
                "\"id\":\"genie.android\",\n" +
                "\"stageid\":\"\"\n" +
                "},\n" +
                "\"comments\":\"Test feedback\",\n" +
                "\"res\":[\n" +
                "{\n" +
                "\"id\":\"value\"\n" +
                "}\n" +
                "]\n" +
                "}\n" +
                "},\n" +
                "\"eid\":\"GE_FEEDBACK\",\n" +
                "\"ets\":1469600855380,\n" +
                "\"gdata\":{\n" +
                "\"id\":\"genie.android\",\n" +
                "\"ver\":\"5.1.localqa-debug.12\"\n" +
                "},\n" +
                "\"sid\":\"\",\n" +
                "\"tags\":[\n" +
                "],\n" +
                "\"ts\":\"2016-07-27T06:27:35+0000\",\n" +
                "\"uid\":\"ae647746-435b-40a9-b21a-f4f28699d269\",\n" +
                "\"ver\":\"2.0\"\n" +
                "}";
    }


    public static String getGeInteractEvent(String id, String stageid, String subtype, String type) {

        String resourceid;
        if (!StringUtil.isNullOrEmpty(id)) {
            resourceid = "\"id\":" + id + "";
        } else {
            resourceid = "\"id\":\"\"";
        }

        return "\n" +
                "{\"extype\":\"\"," + resourceid + ",\"pos\":[],\"stageid\":" + stageid + ",\"subtype\":" + subtype + ",\"tid\":\"\",\"type\":" + type + ",\"uri\":\"\",\"values\":[]\n" +
                "}";
    }

    public static String getGeLaunchGameEvent(String gid, String tmschm) {
        return "\n" +
                "{\"err\":\"\",\"gid\":" + gid + ",\"tmschm\":" + tmschm + "}";

    }

    public static String getGeGameEndEvent(String gid, long length) {
        return "\n" +
                "{\"err\":\"\",\"gid\":" + gid + ",\"length\":" + length + ",\"tmsize\":\"0\"}";

    }


    public static String getGE_INTERACT_Event(String did) {
        return "{\n" +
                "\"did\":\"8cb2e5d897d67af33f3782ea5959e1753f4ae145\",\"edata\":{\"eks\":{\"extype\":\"\",\"id\":\"\",\"pos\":[],\"stageid\":\"Genie-Home-OnBoardingScreen\",\"subtype\":\"\",\"tid\":\"\",\"type\":\"SHOW\",\"uri\":\"\",\"values\":[]}},\"eid\":\"GE_INTERACT\",\"ets\":1473323446353,\"gdata\":{\"id\":\"genie.android\",\"ver\":\"5.2.localintegrationTest-debug.15\"},\"sid\":\"\",\"tags\":[],\"ts\":\"2016-09-08T08:30:46+0000\",\"uid\":\"\",\"ver\":\"2.0\"}";
    }

    public static String getRecommendedContentResponse() {
        return "{\n" +
                "\"id\":\"ekstep.analytics.recommendations\",\n" +
                "\"ver\":\"1.0\",\n" +
                "\"ts\":\"2016-07-29T05:00:24.021+00:00\",\n" +
                "\"params\":{\n" +
                "\"resmsgid\":\"986baa30-94ff-4fad-9503-f1312a82568e\",\n" +
                "\"status\":\"successful\"\n" +
                "},\n" +
                "\"result\":{\n" +
                "\"content\":[\n" +
                "{\n" +
                "\"mediaType\":\"content\",\n" +
                "\"name\":\" ????? ???? 2\",\n" +
                "\"createdOn\":\"2016-03-29T05:04:52.101+0000\",\n" +
                "\"source\":\"EkStep\",\n" +
                "\"genre\":[\n" +
                "\"Chapter Books\"\n" +
                "],\n" +
                "\"lastUpdatedOn\":\"2016-05-18T07:21:43.740+0000\",\n" +
                "\"size\":761678,\n" +
                "\"identifier\":\"domain_14443\",\n" +
                "\"graph_id\":\"domain\",\n" +
                "\"description\":\"????? ???? 2\",\n" +
                "\"gradeLevel\":[\n" +
                "\"Grade 4\"\n" +
                "],\n" +
                "\"node_id\":14443,\n" +
                "\"os\":[\n" +
                "\"All\"\n" +
                "],\n" +
                "\"developer\":\"EkStep\",\n" +
                "\"appIcon\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/language_assets/113937-200_1459227892139.png\",\n" +
                "\"downloadUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/domain_14443_1459343211245.ecar\",\n" +
                "\"mimeType\":\"application/vnd.ekstep.ecml-archive\",\n" +
                "\"portalOwner\":\"EkStep\",\n" +
                "\"code\":\"org.ekstep.ordinal.story\",\n" +
                "\"license\":\"Creative Commons Attribution (CC BY)\",\n" +
                "\"contentType\":\"Worksheet\",\n" +
                "\"language\":[\n" +
                "\"Hindi\"\n" +
                "],\n" +
                "\"ageGroup\":[\n" +
                "\"8-10\"\n" +
                "],\n" +
                "\"lastPublishedOn\":\"2016-03-30T13:06:51.517+0000\",\n" +
                "\"es_metadata_id\":\"domain_14443\",\n" +
                "\"objectType\":\"Content\",\n" +
                "\"status\":\"Live\",\n" +
                "\"publisher\":\"EkStep\",\n" +
                "\"nodeType\":\"DATA_NODE\",\n" +
                "\"idealScreenSize\":\"normal\",\n" +
                "\"owner\":\"EkStep\",\n" +
                "\"osId\":\"org.ekstep.quiz.app\",\n" +
                "\"artifactUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/1459228038101_domain_14443.zip\",\n" +
                "\"reco_score\":0.823,\n" +
                "\"visibility\":\"Default\",\n" +
                "\"pkgVersion\":3,\n" +
                "\"idealScreenDensity\":\"hdpi\"\n" +
                "},\n" +
                "{\n" +
                "\"popularity\":1,\n" +
                "\"mediaType\":\"content\",\n" +
                "\"name\":\" How to Weigh an elephant in Hindi?\",\n" +
                "\"createdOn\":\"2016-03-29T04:39:38.522+0000\",\n" +
                "\"source\":\"EkStep\",\n" +
                "\"genre\":[\n" +
                "\"Picture Books\"\n" +
                "],\n" +
                "\"lastUpdatedOn\":\"2016-06-17T02:29:54.242+0000\",\n" +
                "\"size\":11440979,\n" +
                "\"identifier\":\"domain_14433\",\n" +
                "\"graph_id\":\"domain\",\n" +
                "\"description\":\"I am trying to see if I can successfully translate one story from English to Hindi\",\n" +
                "\"gradeLevel\":[\n" +
                "\"Grade 1\"\n" +
                "],\n" +
                "\"node_id\":14433,\n" +
                "\"os\":[\n" +
                "\"All\"\n" +
                "],\n" +
                "\"collections\":[\n" +
                "\"domain_13464\",\n" +
                "\"domain_61705\",\n" +
                "\"domain_58439\",\n" +
                "\"domain_59129\",\n" +
                "\"domain_13469\",\n" +
                "\"domain_71714\",\n" +
                "\"domain_70420\",\n" +
                "\"domain_70419\",\n" +
                "\"domain_14614\",\n" +
                "\"domain_62264\",\n" +
                "\"domain_61025\",\n" +
                "\"domain_57871\",\n" +
                "\"domain_57870\"\n" +
                "],\n" +
                "\"developer\":\"EkStep\",\n" +
                "\"appIcon\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/language_assets/1452170713095-logo_1459226378582.png\",\n" +
                "\"downloadUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/domain_14433_1459343205901.ecar\",\n" +
                "\"concepts\":[\n" +
                "\"domain_13283\"\n" +
                "],\n" +
                "\"mimeType\":\"application/vnd.ekstep.ecml-archive\",\n" +
                "\"portalOwner\":\"275\",\n" +
                "\"code\":\"org.ekstep.literacy.story.172\",\n" +
                "\"license\":\"Creative Commons Attribution (CC BY)\",\n" +
                "\"contentType\":\"Story\",\n" +
                "\"language\":[\n" +
                "\"Hindi\"\n" +
                "],\n" +
                "\"ageGroup\":[\n" +
                "\"<5\"\n" +
                "],\n" +
                "\"lastPublishedOn\":\"2016-05-03T17:44:50.282+0000\",\n" +
                "\"es_metadata_id\":\"domain_14433\",\n" +
                "\"objectType\":\"Content\",\n" +
                "\"status\":\"Live\",\n" +
                "\"publisher\":\"EkStep\",\n" +
                "\"nodeType\":\"DATA_NODE\",\n" +
                "\"idealScreenSize\":\"normal\",\n" +
                "\"owner\":\"275\",\n" +
                "\"osId\":\"org.ekstep.quiz.app\",\n" +
                "\"artifactUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/1455559521178-org-ekstep-howtoweighanelephant_1459226577470.zip\",\n" +
                "\"reco_score\":0.754,\n" +
                "\"visibility\":\"Default\",\n" +
                "\"pkgVersion\":2,\n" +
                "\"idealScreenDensity\":\"hdpi\"\n" +
                "},\n" +
                "{\n" +
                "\"copyright\":\"\",\n" +
                "\"mediaType\":\"content\",\n" +
                "\"name\":\"34567\",\n" +
                "\"createdOn\":\"2016-06-07T11:22:48.941+0000\",\n" +
                "\"source\":\"EkStep\",\n" +
                "\"lastUpdatedOn\":\"2016-06-07T11:53:21.601+0000\",\n" +
                "\"size\":1246545,\n" +
                "\"identifier\":\"domain_63844\",\n" +
                "\"graph_id\":\"domain\",\n" +
                "\"description\":\"34567\",\n" +
                "\"gradeLevel\":[\n" +
                "\"Kindergarten\",\n" +
                "\"Grade 1\",\n" +
                "\"Grade 2\",\n" +
                "\"Grade 3\",\n" +
                "\"Grade 4\",\n" +
                "\"Grade 5\",\n" +
                "\"Other\"\n" +
                "],\n" +
                "\"domain\":[\n" +
                "\"literacy\"\n" +
                "],\n" +
                "\"node_id\":63844,\n" +
                "\"os\":[\n" +
                "\"All\"\n" +
                "],\n" +
                "\"developer\":\"EkStep\",\n" +
                "\"downloadUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/domain_63844_1465300401140.ecar\",\n" +
                "\"concepts\":[\n" +
                "\"LO53\"\n" +
                "],\n" +
                "\"mimeType\":\"application/vnd.ekstep.ecml-archive\",\n" +
                "\"portalOwner\":\"EkStep\",\n" +
                "\"code\":\"org.ekstep.Array.worksheet.2838\",\n" +
                "\"license\":\"Creative Commons Attribution (CC BY)\",\n" +
                "\"contentType\":\"Worksheet\",\n" +
                "\"language\":[\n" +
                "\"English\"\n" +
                "],\n" +
                "\"ageGroup\":[\n" +
                "\"5-6\"\n" +
                "],\n" +
                "\"lastPublishedOn\":\"2016-06-07T11:53:21.527+0000\",\n" +
                "\"es_metadata_id\":\"domain_63844\",\n" +
                "\"objectType\":\"Content\",\n" +
                "\"lastUpdatedBy\":\"345\",\n" +
                "\"status\":\"Live\",\n" +
                "\"publisher\":\"EkStep\",\n" +
                "\"imageCredits\":[\n" +
                "\"ekstep\"\n" +
                "],\n" +
                "\"nodeType\":\"DATA_NODE\",\n" +
                "\"lastSubmittedOn\":\"2016-06-07T11:51:53.411+0000\",\n" +
                "\"idealScreenSize\":\"normal\",\n" +
                "\"owner\":\"EkStep\",\n" +
                "\"osId\":\"org.ekstep.quiz.app\",\n" +
                "\"soundCredits\":[\n" +
                "\"ekstep\"\n" +
                "],\n" +
                "\"artifactUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/1465300399686_domain_63844.zip\",\n" +
                "\"reco_score\":0.632,\n" +
                "\"visibility\":\"Default\",\n" +
                "\"pkgVersion\":2,\n" +
                "\"idealScreenDensity\":\"hdpi\"\n" +
                "},\n" +
                "{\n" +
                "\"mediaType\":\"content\",\n" +
                "\"name\":\"??\",\n" +
                "\"createdOn\":\"2016-04-14T05:38:16.396+0000\",\n" +
                "\"source\":\"EkStep\",\n" +
                "\"genre\":[\n" +
                "\"Picture Books\"\n" +
                "],\n" +
                "\"lastUpdatedOn\":\"2016-05-18T07:21:44.335+0000\",\n" +
                "\"size\":1014298,\n" +
                "\"identifier\":\"domain_38245\",\n" +
                "\"graph_id\":\"domain\",\n" +
                "\"description\":\"Pratham Mysore Level 1 Saralakshara Story \",\n" +
                "\"gradeLevel\":[\n" +
                "\"Grade 1\"\n" +
                "],\n" +
                "\"node_id\":38245,\n" +
                "\"os\":[\n" +
                "\"All\"\n" +
                "],\n" +
                "\"collections\":[\n" +
                "\"domain_13347\"\n" +
                "],\n" +
                "\"developer\":\"EkStep\",\n" +
                "\"downloadUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/domain_38245_1460697261758.ecar\",\n" +
                "\"mimeType\":\"application/vnd.ekstep.ecml-archive\",\n" +
                "\"portalOwner\":\"EkStep\",\n" +
                "\"code\":\"org.ekstep.ordinal.story\",\n" +
                "\"license\":\"Creative Commons Attribution (CC BY)\",\n" +
                "\"contentType\":\"Story\",\n" +
                "\"language\":[\n" +
                "\"Kannada\"\n" +
                "],\n" +
                "\"ageGroup\":[\n" +
                "\"5-6\"\n" +
                "],\n" +
                "\"lastPublishedOn\":\"2016-04-28T09:46:04.002+0000\",\n" +
                "\"es_metadata_id\":\"domain_38245\",\n" +
                "\"objectType\":\"Content\",\n" +
                "\"status\":\"Live\",\n" +
                "\"publisher\":\"EkStep\",\n" +
                "\"nodeType\":\"DATA_NODE\",\n" +
                "\"idealScreenSize\":\"normal\",\n" +
                "\"owner\":\"EkStep\",\n" +
                "\"osId\":\"org.ekstep.quiz.app\",\n" +
                "\"artifactUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/1460697260001_domain_38245.zip\",\n" +
                "\"reco_score\":0.5,\n" +
                "\"visibility\":\"Default\",\n" +
                "\"pkgVersion\":1,\n" +
                "\"idealScreenDensity\":\"hdpi\"\n" +
                "},\n" +
                "{\n" +
                "\"popularity\":1,\n" +
                "\"mediaType\":\"content\",\n" +
                "\"name\":\"??\",\n" +
                "\"createdOn\":\"2016-03-29T04:17:19.085+0000\",\n" +
                "\"source\":\"EkStep\",\n" +
                "\"genre\":[\n" +
                "\"Picture Books\"\n" +
                "],\n" +
                "\"lastUpdatedOn\":\"2016-05-18T07:21:43.249+0000\",\n" +
                "\"size\":1964170,\n" +
                "\"identifier\":\"domain_14426\",\n" +
                "\"graph_id\":\"domain\",\n" +
                "\"description\":\"Teaching 9 alphabets and 2 matras \",\n" +
                "\"gradeLevel\":[\n" +
                "\"Grade 1\"\n" +
                "],\n" +
                "\"tags\":[\n" +
                "\"Eid\"\n" +
                "],\n" +
                "\"node_id\":14426,\n" +
                "\"os\":[\n" +
                "\"All\"\n" +
                "],\n" +
                "\"developer\":\"EkStep\",\n" +
                "\"appIcon\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/language_assets/IMG-1299_1459225109384.jpg\",\n" +
                "\"downloadUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/domain_14426_1459407615853.ecar\",\n" +
                "\"concepts\":[\n" +
                "\"LO39\"\n" +
                "],\n" +
                "\"mimeType\":\"application/vnd.ekstep.ecml-archive\",\n" +
                "\"portalOwner\":\"EkStep\",\n" +
                "\"code\":\"org.ekstep.literacy.story.171\",\n" +
                "\"license\":\"Creative Commons Attribution (CC BY)\",\n" +
                "\"contentType\":\"Story\",\n" +
                "\"language\":[\n" +
                "\"Hindi\"\n" +
                "],\n" +
                "\"ageGroup\":[\n" +
                "\"5-6\"\n" +
                "],\n" +
                "\"lastPublishedOn\":\"2016-05-03T17:44:49.155+0000\",\n" +
                "\"es_metadata_id\":\"domain_14426\",\n" +
                "\"objectType\":\"Content\",\n" +
                "\"status\":\"Live\",\n" +
                "\"publisher\":\"EkStep\",\n" +
                "\"nodeType\":\"DATA_NODE\",\n" +
                "\"idealScreenSize\":\"normal\",\n" +
                "\"owner\":\"EkStep\",\n" +
                "\"osId\":\"org.ekstep.quiz.app\",\n" +
                "\"artifactUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/1459404594007_domain_14426.zip\",\n" +
                "\"collaborators\":[\n" +
                "\"141\",\n" +
                "\"184\",\n" +
                "\"239\",\n" +
                "\"143\",\n" +
                "\"275\"\n" +
                "],\n" +
                "\"reco_score\":0.2,\n" +
                "\"visibility\":\"Default\",\n" +
                "\"pkgVersion\":4,\n" +
                "\"idealScreenDensity\":\"hdpi\"\n" +
                "}\n" +
                "]\n" +
                "}\n" +
                "}";
    }

    public static String getPageAssembleResponseData() {
        return "{\n" +
                "\"id\":\"org.ekstep.genie.content.home\",\n" +
                "\"ver\":\"1.0\",\n" +
                "\"ts\":\"2016-08-10T08:52:18+00:00\",\n" +
                "\"params\":{\n" +
                "\"resmsgid\":\"f3e0c81fe95c6caab7223198b6501bed3cfe61d5\",\n" +
                "\"msgid\":\"\",\n" +
                "\"status\":\"successful\",\n" +
                "\"err\":\"\",\n" +
                "\"errmsg\":\"\"\n" +
                "},\n" +
                "\"result\":{\n" +
                "\"page\":{\n" +
                "\"id\":\"org.ekstep.genie.content.home\",\n" +
                "\"banners\":[\n" +
                "{\n" +
                "\"alternateText\":\"Stories\",\n" +
                "\"imageUrl\":\"http://i.imgur.com/fhWNbjd.png\",\n" +
                "\"url\":\"http://www.ekstep.in/l/all?type=story\\u0026sort=popularity\"\n" +
                "}\n" +
                "],\n" +
                "\"sections\":[\n" +
                "{\n" +
                "\"display\":{\n" +
                "\"name\":{\n" +
                "\"en\":\"Recommended\",\n" +
                "\"hn\":\"à¤¸à¤²à¤¾à¤¹\"\n" +
                "}\n" +
                "},\n" +
                "\"search\":null,\n" +
                "\"recommend\":{\n" +
                "\"context\":{\n" +
                "\"contentid\":\"\",\n" +
                "\"did\":\"517115868495bee6120045db8c1c4a2181156508\",\n" +
                "\"dlang\":\"en\",\n" +
                "\"uid\":\"ab86af28-1a73-4adf-b30b-e4d354276a90\"\n" +
                "},\n" +
                "\"query\":\"\",\n" +
                "\"limit\":10,\n" +
                "\"sort_by\":null,\n" +
                "\"filters\":null,\n" +
                "\"facets\":null\n" +
                "},\n" +
                "\"contents\":[\n" +
                "{\n" +
                "\"ageGroup\":[\n" +
                "\"5-6\"\n" +
                "],\n" +
                "\"artifactUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/build-a-sentence_1470314896205.zip\",\n" +
                "\"code\":\"Test_QA\",\n" +
                "\"contentType\":\"Story\",\n" +
                "\"createdOn\":\"2016-08-04T12:48:14.506+0000\",\n" +
                "\"description\":\"Test_QA\",\n" +
                "\"downloadUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/test_qa_7460267_1470314896812_test_qa_7460267.ecar\",\n" +
                "\"es_metadata_id\":\"Test_QA_7460267\",\n" +
                "\"gradeLevel\":[\n" +
                "\"Grade 1\"\n" +
                "],\n" +
                "\"graph_id\":\"domain\",\n" +
                "\"idealScreenDensity\":\"hdpi\",\n" +
                "\"idealScreenSize\":\"normal\",\n" +
                "\"identifier\":\"Test_QA_7460267\",\n" +
                "\"language\":[\n" +
                "\"English\"\n" +
                "],\n" +
                "\"lastPublishedOn\":\"2016-08-04T12:48:17.150+0000\",\n" +
                "\"lastUpdatedOn\":\"2016-08-04T12:48:17.210+0000\",\n" +
                "\"license\":\"Creative Commons Attribution (CC BY)\",\n" +
                "\"mediaType\":\"content\",\n" +
                "\"mimeType\":\"application/vnd.ekstep.html-archive\",\n" +
                "\"name\":\"Test_QA_7460267\",\n" +
                "\"nodeType\":\"DATA_NODE\",\n" +
                "\"node_id\":44870,\n" +
                "\"objectType\":\"Content\",\n" +
                "\"os\":[\n" +
                "\"All\"\n" +
                "],\n" +
                "\"osId\":\"org.ekstep.quiz.app\",\n" +
                "\"owner\":\"EkStep\",\n" +
                "\"pkgVersion\":4,\n" +
                "\"reco_score\":7378.86,\n" +
                "\"size\":1.861486e+06,\n" +
                "\"status\":\"Live\",\n" +
                "\"visibility\":\"Default\"\n" +
                "},\n" +
                "{\n" +
                "\"ageGroup\":[\n" +
                "\"5-6\"\n" +
                "],\n" +
                "\"appIcon\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/blue-equals_1459190093659.thumb.png\",\n" +
                "\"artifactUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/1469202115990_do_20043770.zip\",\n" +
                "\"code\":\"test_cwp_33\",\n" +
                "\"concepts\":[\n" +
                "\"Num:C5:SC2\"\n" +
                "],\n" +
                "\"contentType\":\"Story\",\n" +
                "\"createdOn\":\"2016-07-22T15:38:40.850+0000\",\n" +
                "\"description\":\"Test Assets Missing Issue\",\n" +
                "\"downloadUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/test-assets-missing-issue_1469202116544_do_20043770.ecar\",\n" +
                "\"es_metadata_id\":\"do_20043770\",\n" +
                "\"gradeLevel\":[\n" +
                "\"Grade 1\"\n" +
                "],\n" +
                "\"graph_id\":\"domain\",\n" +
                "\"idealScreenDensity\":\"hdpi\",\n" +
                "\"idealScreenSize\":\"normal\",\n" +
                "\"identifier\":\"do_20043770\",\n" +
                "\"language\":[\n" +
                "\"Hindi\"\n" +
                "],\n" +
                "\"lastPublishedOn\":\"2016-07-22T15:41:57.037+0000\",\n" +
                "\"lastUpdatedOn\":\"2016-07-22T15:41:57.123+0000\",\n" +
                "\"license\":\"Creative Commons Attribution (CC BY)\",\n" +
                "\"mediaType\":\"content\",\n" +
                "\"mimeType\":\"application/vnd.ekstep.ecml-archive\",\n" +
                "\"name\":\"Test Assets Missing Issue\",\n" +
                "\"nodeType\":\"DATA_NODE\",\n" +
                "\"node_id\":43770,\n" +
                "\"objectType\":\"Content\",\n" +
                "\"os\":[\n" +
                "\"All\"\n" +
                "],\n" +
                "\"osId\":\"org.ekstep.quiz.app\",\n" +
                "\"owner\":\"EkStep\",\n" +
                "\"pkgVersion\":2,\n" +
                "\"portalOwner\":\"EkStep\",\n" +
                "\"posterImage\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/language_assets/blue-equals_1459190093659.png\",\n" +
                "\"reco_score\":7378.86,\n" +
                "\"size\":3.395068e+06,\n" +
                "\"status\":\"Live\",\n" +
                "\"visibility\":\"Default\"\n" +
                "},\n" +
                "{\n" +
                "\"ageGroup\":[\n" +
                "\"5-6\"\n" +
                "],\n" +
                "\"appIcon\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/screenshot-sd2016-07-14-13-50-53_200_1468486312_1468486346605.thumb.png\",\n" +
                "\"artifactUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/1468489122296_do_20043292.zip\",\n" +
                "\"code\":\"org.ekstep.literacy.story.1171\",\n" +
                "\"concepts\":[\n" +
                "\"LO1\"\n" +
                "],\n" +
                "\"contentType\":\"Story\",\n" +
                "\"createdOn\":\"2016-07-14T08:50:43.177+0000\",\n" +
                "\"description\":\"zoo1test2\",\n" +
                "\"domain\":[\n" +
                "\"literacy\"\n" +
                "],\n" +
                "\"downloadUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/zoo1test2_1468489124246_do_20043292.ecar\",\n" +
                "\"es_metadata_id\":\"do_20043292\",\n" +
                "\"gradeLevel\":[\n" +
                "\"Kindergarten\",\n" +
                "\"Grade 1\",\n" +
                "\"Grade 2\",\n" +
                "\"Grade 3\",\n" +
                "\"Grade 4\",\n" +
                "\"Grade 5\",\n" +
                "\"Other\"\n" +
                "],\n" +
                "\"graph_id\":\"domain\",\n" +
                "\"idealScreenDensity\":\"hdpi\",\n" +
                "\"idealScreenSize\":\"normal\",\n" +
                "\"identifier\":\"do_20043292\",\n" +
                "\"language\":[\n" +
                "\"English\"\n" +
                "],\n" +
                "\"lastPublishedOn\":\"2016-07-14T09:38:46.050+0000\",\n" +
                "\"lastSubmittedOn\":\"2016-07-14T09:37:26.766+0000\",\n" +
                "\"lastUpdatedBy\":\"329\",\n" +
                "\"lastUpdatedOn\":\"2016-07-14T09:38:46.110+0000\",\n" +
                "\"license\":\"Creative Commons Attribution (CC BY)\",\n" +
                "\"mediaType\":\"content\",\n" +
                "\"mimeType\":\"application/vnd.ekstep.ecml-archive\",\n" +
                "\"name\":\"zoo1test2\",\n" +
                "\"nodeType\":\"DATA_NODE\",\n" +
                "\"node_id\":43292,\n" +
                "\"objectType\":\"Content\",\n" +
                "\"os\":[\n" +
                "\"All\"\n" +
                "],\n" +
                "\"osId\":\"org.ekstep.quiz.app\",\n" +
                "\"owner\":\"Srivathsa Dhanraj\",\n" +
                "\"pkgVersion\":1,\n" +
                "\"portalOwner\":\"200\",\n" +
                "\"posterImage\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/language_assets/screenshot-sd2016-07-14-13-50-53_200_1468486312_1468486346605.png\",\n" +
                "\"reco_score\":7378.86,\n" +
                "\"size\":1.441362e+07,\n" +
                "\"status\":\"Live\",\n" +
                "\"visibility\":\"Default\"\n" +
                "},\n" +
                "{\n" +
                "\"ageGroup\":[\n" +
                "\"\\u003c5\"\n" +
                "],\n" +
                "\"appIcon\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/1463065467938icon_submit_1459243202199.png\",\n" +
                "\"artifactUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/1470218513531_do_20044452.zip\",\n" +
                "\"code\":\"org.ekstep.literacy.story.1540\",\n" +
                "\"concepts\":[\n" +
                "\"LO53\"\n" +
                "],\n" +
                "\"contentType\":\"Story\",\n" +
                "\"copyright\":\"Ek Step\",\n" +
                "\"createdOn\":\"2016-08-03T09:02:30.343+0000\",\n" +
                "\"description\":\"à¤šà¤¾à¤à¤¦ à¤”à¤° à¤¤à¤¾à¤°à¥‡\",\n" +
                "\"domain\":[\n" +
                "\"literacy\"\n" +
                "],\n" +
                "\"downloadUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/caand-aur-taare_1470218513848_do_20044452.ecar\",\n" +
                "\"es_metadata_id\":\"do_20044452\",\n" +
                "\"gradeLevel\":[\n" +
                "\"Kindergarten\"\n" +
                "],\n" +
                "\"graph_id\":\"domain\",\n" +
                "\"idealScreenDensity\":\"hdpi\",\n" +
                "\"idealScreenSize\":\"normal\",\n" +
                "\"identifier\":\"do_20044452\",\n" +
                "\"imageCredits\":[\n" +
                "\"ekstep\"\n" +
                "],\n" +
                "\"language\":[\n" +
                "\"Hindi\"\n" +
                "],\n" +
                "\"lastPublishedOn\":\"2016-08-03T10:01:54.152+0000\",\n" +
                "\"lastSubmittedOn\":\"2016-08-03T09:53:00.528+0000\",\n" +
                "\"lastUpdatedBy\":\"200\",\n" +
                "\"lastUpdatedOn\":\"2016-08-03T10:01:54.231+0000\",\n" +
                "\"license\":\"Creative Commons Attribution (CC BY)\",\n" +
                "\"mediaType\":\"content\",\n" +
                "\"mimeType\":\"application/vnd.ekstep.ecml-archive\",\n" +
                "\"name\":\"à¤šà¤¾à¤à¤¦ à¤”à¤° à¤¤à¤¾à¤°à¥‡\",\n" +
                "\"nodeType\":\"DATA_NODE\",\n" +
                "\"node_id\":44452,\n" +
                "\"objectType\":\"Content\",\n" +
                "\"os\":[\n" +
                "\"All\"\n" +
                "],\n" +
                "\"osId\":\"org.ekstep.quiz.app\",\n" +
                "\"owner\":\"Bhawani Shekhawat\",\n" +
                "\"pkgVersion\":1,\n" +
                "\"portalOwner\":\"400\",\n" +
                "\"publisher\":\"Ek Step\",\n" +
                "\"reco_score\":7378.86,\n" +
                "\"size\":1.194936e+06,\n" +
                "\"soundCredits\":[\n" +
                "\"ekstep\"\n" +
                "],\n" +
                "\"status\":\"Live\",\n" +
                "\"visibility\":\"Default\"\n" +
                "},\n" +
                "{\n" +
                "\"ageGroup\":[\n" +
                "\"5-6\"\n" +
                "],\n" +
                "\"artifactUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/ecml_with_json_1470294442877.zip\",\n" +
                "\"code\":\"Test_QA\",\n" +
                "\"contentType\":\"Story\",\n" +
                "\"createdOn\":\"2016-08-04T07:07:22.191+0000\",\n" +
                "\"description\":\"Test_QA\",\n" +
                "\"downloadUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/test_qa_4208355_1470294443846_test_qa_4208355.ecar\",\n" +
                "\"es_metadata_id\":\"Test_QA_4208355\",\n" +
                "\"gradeLevel\":[\n" +
                "\"Grade 1\"\n" +
                "],\n" +
                "\"graph_id\":\"domain\",\n" +
                "\"idealScreenDensity\":\"hdpi\",\n" +
                "\"idealScreenSize\":\"normal\",\n" +
                "\"identifier\":\"Test_QA_4208355\",\n" +
                "\"language\":[\n" +
                "\"English\"\n" +
                "],\n" +
                "\"lastPublishedOn\":\"2016-08-04T07:07:24.163+0000\",\n" +
                "\"lastUpdatedOn\":\"2016-08-04T07:07:24.217+0000\",\n" +
                "\"license\":\"Creative Commons Attribution (CC BY)\",\n" +
                "\"mediaType\":\"content\",\n" +
                "\"mimeType\":\"application/vnd.ekstep.ecml-archive\",\n" +
                "\"name\":\"Test_QA_4208355\",\n" +
                "\"nodeType\":\"DATA_NODE\",\n" +
                "\"node_id\":44623,\n" +
                "\"objectType\":\"Content\",\n" +
                "\"os\":[\n" +
                "\"All\"\n" +
                "],\n" +
                "\"osId\":\"org.ekstep.quiz.app\",\n" +
                "\"owner\":\"EkStep\",\n" +
                "\"pkgVersion\":4,\n" +
                "\"reco_score\":7378.86,\n" +
                "\"size\":1.227681e+06,\n" +
                "\"status\":\"Live\",\n" +
                "\"visibility\":\"Default\"\n" +
                "},\n" +
                "{\n" +
                "\"ageGroup\":[\n" +
                "\"5-6\"\n" +
                "],\n" +
                "\"artifactUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/ecml_without_asset_1470287225334.zip\",\n" +
                "\"code\":\"Test_QA\",\n" +
                "\"contentType\":\"Story\",\n" +
                "\"createdOn\":\"2016-08-04T05:07:04.218+0000\",\n" +
                "\"description\":\"Test_QA\",\n" +
                "\"downloadUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/test_qa_916187_1470287226526_test_qa_916187.ecar\",\n" +
                "\"es_metadata_id\":\"Test_QA_916187\",\n" +
                "\"gradeLevel\":[\n" +
                "\"Grade 1\"\n" +
                "],\n" +
                "\"graph_id\":\"domain\",\n" +
                "\"idealScreenDensity\":\"hdpi\",\n" +
                "\"idealScreenSize\":\"normal\",\n" +
                "\"identifier\":\"Test_QA_916187\",\n" +
                "\"language\":[\n" +
                "\"English\"\n" +
                "],\n" +
                "\"lastPublishedOn\":\"2016-08-04T05:07:06.690+0000\",\n" +
                "\"lastUpdatedOn\":\"2016-08-04T05:07:06.761+0000\",\n" +
                "\"license\":\"Creative Commons Attribution (CC BY)\",\n" +
                "\"mediaType\":\"content\",\n" +
                "\"mimeType\":\"application/vnd.ekstep.ecml-archive\",\n" +
                "\"name\":\"Test_QA_916187\",\n" +
                "\"nodeType\":\"DATA_NODE\",\n" +
                "\"node_id\":44535,\n" +
                "\"objectType\":\"Content\",\n" +
                "\"os\":[\n" +
                "\"All\"\n" +
                "],\n" +
                "\"osId\":\"org.ekstep.quiz.app\",\n" +
                "\"owner\":\"EkStep\",\n" +
                "\"pkgVersion\":4,\n" +
                "\"reco_score\":7378.86,\n" +
                "\"size\":2939,\n" +
                "\"status\":\"Live\",\n" +
                "\"visibility\":\"Default\"\n" +
                "},\n" +
                "{\n" +
                "\"ageGroup\":[\n" +
                "\"5-6\"\n" +
                "],\n" +
                "\"artifactUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/build-a-sentence_1470311588738.zip\",\n" +
                "\"code\":\"Test_QA\",\n" +
                "\"contentType\":\"Story\",\n" +
                "\"createdOn\":\"2016-08-04T11:53:07.029+0000\",\n" +
                "\"description\":\"Test_QA\",\n" +
                "\"downloadUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/test_qa_3138866_1470311589424_test_qa_3138866.ecar\",\n" +
                "\"es_metadata_id\":\"Test_QA_3138866\",\n" +
                "\"gradeLevel\":[\n" +
                "\"Grade 1\"\n" +
                "],\n" +
                "\"graph_id\":\"domain\",\n" +
                "\"idealScreenDensity\":\"hdpi\",\n" +
                "\"idealScreenSize\":\"normal\",\n" +
                "\"identifier\":\"Test_QA_3138866\",\n" +
                "\"language\":[\n" +
                "\"English\"\n" +
                "],\n" +
                "\"lastPublishedOn\":\"2016-08-04T11:53:09.777+0000\",\n" +
                "\"lastUpdatedOn\":\"2016-08-04T11:53:09.854+0000\",\n" +
                "\"license\":\"Creative Commons Attribution (CC BY)\",\n" +
                "\"mediaType\":\"content\",\n" +
                "\"mimeType\":\"application/vnd.ekstep.html-archive\",\n" +
                "\"name\":\"Test_QA_3138866\",\n" +
                "\"nodeType\":\"DATA_NODE\",\n" +
                "\"node_id\":44813,\n" +
                "\"objectType\":\"Content\",\n" +
                "\"os\":[\n" +
                "\"All\"\n" +
                "],\n" +
                "\"osId\":\"org.ekstep.quiz.app\",\n" +
                "\"owner\":\"EkStep\",\n" +
                "\"pkgVersion\":4,\n" +
                "\"reco_score\":7378.86,\n" +
                "\"size\":1.861484e+06,\n" +
                "\"status\":\"Live\",\n" +
                "\"visibility\":\"Default\"\n" +
                "},\n" +
                "{\n" +
                "\"ageGroup\":[\n" +
                "\"5-6\"\n" +
                "],\n" +
                "\"artifactUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/data_json_ecml_1470314601757.zip\",\n" +
                "\"code\":\"Test_QA\",\n" +
                "\"contentType\":\"Story\",\n" +
                "\"createdOn\":\"2016-08-04T12:43:20.307+0000\",\n" +
                "\"description\":\"Test_QA\",\n" +
                "\"downloadUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/test_qa_6592781_1470314610665_test_qa_6592781.ecar\",\n" +
                "\"es_metadata_id\":\"Test_QA_6592781\",\n" +
                "\"gradeLevel\":[\n" +
                "\"Grade 1\"\n" +
                "],\n" +
                "\"graph_id\":\"domain\",\n" +
                "\"idealScreenDensity\":\"hdpi\",\n" +
                "\"idealScreenSize\":\"normal\",\n" +
                "\"identifier\":\"Test_QA_6592781\",\n" +
                "\"language\":[\n" +
                "\"English\"\n" +
                "],\n" +
                "\"lastPublishedOn\":\"2016-08-04T12:43:31.726+0000\",\n" +
                "\"lastUpdatedOn\":\"2016-08-04T12:43:31.797+0000\",\n" +
                "\"license\":\"Creative Commons Attribution (CC BY)\",\n" +
                "\"mediaType\":\"content\",\n" +
                "\"mimeType\":\"application/vnd.ekstep.ecml-archive\",\n" +
                "\"name\":\"Test_QA_6592781\",\n" +
                "\"nodeType\":\"DATA_NODE\",\n" +
                "\"node_id\":44844,\n" +
                "\"objectType\":\"Content\",\n" +
                "\"os\":[\n" +
                "\"All\"\n" +
                "],\n" +
                "\"osId\":\"org.ekstep.quiz.app\",\n" +
                "\"owner\":\"EkStep\",\n" +
                "\"pkgVersion\":4,\n" +
                "\"reco_score\":7378.86,\n" +
                "\"size\":6.976416e+06,\n" +
                "\"status\":\"Live\",\n" +
                "\"visibility\":\"Default\"\n" +
                "},\n" +
                "{\n" +
                "\"ageGroup\":[\n" +
                "\"5-6\"\n" +
                "],\n" +
                "\"artifactUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/custom_plugin_1470311784427.zip\",\n" +
                "\"code\":\"Test_QA\",\n" +
                "\"contentType\":\"Story\",\n" +
                "\"createdOn\":\"2016-08-04T11:56:19.214+0000\",\n" +
                "\"description\":\"Test_QA\",\n" +
                "\"downloadUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/test_qa_5614748_1470311794419_test_qa_5614748.ecar\",\n" +
                "\"es_metadata_id\":\"Test_QA_5614748\",\n" +
                "\"gradeLevel\":[\n" +
                "\"Grade 1\"\n" +
                "],\n" +
                "\"graph_id\":\"domain\",\n" +
                "\"idealScreenDensity\":\"hdpi\",\n" +
                "\"idealScreenSize\":\"normal\",\n" +
                "\"identifier\":\"Test_QA_5614748\",\n" +
                "\"language\":[\n" +
                "\"English\"\n" +
                "],\n" +
                "\"lastPublishedOn\":\"2016-08-04T11:56:35.451+0000\",\n" +
                "\"lastUpdatedOn\":\"2016-08-04T11:56:35.510+0000\",\n" +
                "\"license\":\"Creative Commons Attribution (CC BY)\",\n" +
                "\"mediaType\":\"content\",\n" +
                "\"mimeType\":\"application/vnd.ekstep.ecml-archive\",\n" +
                "\"name\":\"Test_QA_5614748\",\n" +
                "\"nodeType\":\"DATA_NODE\",\n" +
                "\"node_id\":44820,\n" +
                "\"objectType\":\"Content\",\n" +
                "\"os\":[\n" +
                "\"All\"\n" +
                "],\n" +
                "\"osId\":\"org.ekstep.quiz.app\",\n" +
                "\"owner\":\"EkStep\",\n" +
                "\"pkgVersion\":4,\n" +
                "\"reco_score\":7378.86,\n" +
                "\"size\":7.351128e+06,\n" +
                "\"status\":\"Live\",\n" +
                "\"visibility\":\"Default\"\n" +
                "},\n" +
                "{\n" +
                "\"ageGroup\":[\n" +
                "\"5-6\"\n" +
                "],\n" +
                "\"appIcon\":\"https://qa.ekstep.in/assets/public/content/1463065061997btn_ok_highlights_1460705843676.png\",\n" +
                "\"artifactUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/esl-lesson-1-part-2-fix-598_239_1470388830_1470388904616.zip\",\n" +
                "\"code\":\"org.ekstep.literacy.story.1642\",\n" +
                "\"contentType\":\"Story\",\n" +
                "\"createdOn\":\"2016-08-05T09:18:59.810+0000\",\n" +
                "\"description\":\"test\",\n" +
                "\"domain\":[\n" +
                "\"literacy\"\n" +
                "],\n" +
                "\"downloadUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/rtest_1470389304115_do_20045048.ecar\",\n" +
                "\"es_metadata_id\":\"do_20045048\",\n" +
                "\"gradeLevel\":[\n" +
                "\"Kindergarten\",\n" +
                "\"Grade 1\",\n" +
                "\"Grade 2\",\n" +
                "\"Grade 3\",\n" +
                "\"Grade 4\",\n" +
                "\"Grade 5\",\n" +
                "\"Other\"\n" +
                "],\n" +
                "\"graph_id\":\"domain\",\n" +
                "\"idealScreenDensity\":\"hdpi\",\n" +
                "\"idealScreenSize\":\"normal\",\n" +
                "\"identifier\":\"do_20045048\",\n" +
                "\"language\":[\n" +
                "\"Tamil\"\n" +
                "],\n" +
                "\"lastPublishedOn\":\"2016-08-05T09:28:27.767+0000\",\n" +
                "\"lastSubmittedOn\":\"2016-08-05T09:27:34.650+0000\",\n" +
                "\"lastUpdatedBy\":\"382\",\n" +
                "\"lastUpdatedOn\":\"2016-08-05T09:28:27.841+0000\",\n" +
                "\"license\":\"Creative Commons Attribution (CC BY)\",\n" +
                "\"mediaType\":\"content\",\n" +
                "\"mimeType\":\"application/vnd.ekstep.ecml-archive\",\n" +
                "\"name\":\"rtest\",\n" +
                "\"nodeType\":\"DATA_NODE\",\n" +
                "\"node_id\":45048,\n" +
                "\"objectType\":\"Content\",\n" +
                "\"os\":[\n" +
                "\"All\"\n" +
                "],\n" +
                "\"osId\":\"org.ekstep.quiz.app\",\n" +
                "\"owner\":\"External Testing vendor\",\n" +
                "\"pkgVersion\":2,\n" +
                "\"portalOwner\":\"239\",\n" +
                "\"reco_score\":7378.86,\n" +
                "\"size\":2.7295345e+07,\n" +
                "\"status\":\"Live\",\n" +
                "\"visibility\":\"Default\"\n" +
                "}\n" +
                "]\n" +
                "},\n" +
                "{\n" +
                "\"display\":{\n" +
                "\"name\":{\n" +
                "\"en\":\"Most Popular\",\n" +
                "\"hn\":\"à¤¸à¤¬à¤¸à¥‡ à¤²à¥‹à¤•à¤ªà¥à¤°à¤¿à¤¯\"\n" +
                "}\n" +
                "},\n" +
                "\"search\":{\n" +
                "\"query\":\"\",\n" +
                "\"limit\":10,\n" +
                "\"sort_by\":{\n" +
                "\"popularity\":\"desc\"\n" +
                "},\n" +
                "\"filters\":{\n" +
                "\"contentType\":[\n" +
                "\"Story\",\n" +
                "\"Worksheet\",\n" +
                "\"Collection\"\n" +
                "],\n" +
                "\"objectType\":[\n" +
                "\"Content\"\n" +
                "],\n" +
                "\"status\":[\n" +
                "\"Live\"\n" +
                "]\n" +
                "},\n" +
                "\"facets\":[\n" +
                "\"contentType\",\n" +
                "\"domain\",\n" +
                "\"ageGroup\",\n" +
                "\"language\",\n" +
                "\"gradeLevel\"\n" +
                "]\n" +
                "},\n" +
                "\"recommend\":null,\n" +
                "\"contents\":[\n" +
                "{\n" +
                "\"ageGroup\":[\n" +
                "\"5-6\",\n" +
                "\"6-7\",\n" +
                "\"7-8\",\n" +
                "\"8-10\"\n" +
                "],\n" +
                "\"appIcon\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/language_assets/slide15_1466528396519.jpg\",\n" +
                "\"artifactUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/1467631264095_do_30014521.zip\",\n" +
                "\"code\":\"org.ekstep.numeracy.worksheet.552\",\n" +
                "\"collections\":[\n" +
                "\"do_30022230\"\n" +
                "],\n" +
                "\"concepts\":[\n" +
                "\"Num:C3:SC3:MC8\"\n" +
                "],\n" +
                "\"contentType\":\"Worksheet\",\n" +
                "\"copyright\":\"CC0\",\n" +
                "\"createdOn\":\"2016-06-21T16:33:56.214+0000\",\n" +
                "\"description\":\"This worksheet contains 10 questions from the multiplication table of 16. It is for students learning multiplication tables of 2-digit numbers. For each question, feedback is provided.\",\n" +
                "\"domain\":[\n" +
                "\"numeracy\"\n" +
                "],\n" +
                "\"downloadUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/do_30014521_1467631264656.ecar\",\n" +
                "\"es_metadata_id\":\"do_30014521\",\n" +
                "\"gradeLevel\":[\n" +
                "\"Grade 1\",\n" +
                "\"Grade 2\",\n" +
                "\"Grade 3\",\n" +
                "\"Grade 4\",\n" +
                "\"Grade 5\",\n" +
                "\"Other\"\n" +
                "],\n" +
                "\"graph_id\":\"domain\",\n" +
                "\"idealScreenDensity\":\"hdpi\",\n" +
                "\"idealScreenSize\":\"normal\",\n" +
                "\"identifier\":\"do_30014521\",\n" +
                "\"imageCredits\":[\n" +
                "\"ekstep\",\n" +
                "\"Parabal Singh\",\n" +
                "\"EkStep\"\n" +
                "],\n" +
                "\"language\":[\n" +
                "\"English\"\n" +
                "],\n" +
                "\"lastPublishedOn\":\"2016-07-04T11:21:06.409+0000\",\n" +
                "\"lastSubmittedOn\":\"2016-07-04T09:55:46.408+0000\",\n" +
                "\"lastUpdatedBy\":\"237\",\n" +
                "\"lastUpdatedOn\":\"2016-07-05T09:46:49.996+0000\",\n" +
                "\"license\":\"Creative Commons Attribution (CC BY)\",\n" +
                "\"mediaType\":\"content\",\n" +
                "\"mimeType\":\"application/vnd.ekstep.ecml-archive\",\n" +
                "\"name\":\"Multiplication - 16 Times Table\",\n" +
                "\"nodeType\":\"DATA_NODE\",\n" +
                "\"node_id\":14521,\n" +
                "\"objectType\":\"Content\",\n" +
                "\"optStatus\":\"Complete\",\n" +
                "\"os\":[\n" +
                "\"All\"\n" +
                "],\n" +
                "\"osId\":\"org.ekstep.quiz.app\",\n" +
                "\"owner\":\"Parabal Partap Singh\",\n" +
                "\"pkgVersion\":2,\n" +
                "\"portalOwner\":\"334\",\n" +
                "\"publisher\":\"\",\n" +
                "\"size\":1.508889e+06,\n" +
                "\"soundCredits\":[\n" +
                "\"ekstep\"\n" +
                "],\n" +
                "\"source\":\"EkStep\",\n" +
                "\"status\":\"Live\",\n" +
                "\"tags\":[\n" +
                "\"table of 16\",\n" +
                "\"16 times table\",\n" +
                "\"multiply\",\n" +
                "\"multiplication\",\n" +
                "\"multiplication table\",\n" +
                "\"multiplication tables\",\n" +
                "\"times table\",\n" +
                "\"tables\"\n" +
                "],\n" +
                "\"visibility\":\"Default\"\n" +
                "},\n" +
                "{\n" +
                "\"ageGroup\":[\n" +
                "\"5-6\",\n" +
                "\"6-7\",\n" +
                "\"7-8\"\n" +
                "],\n" +
                "\"appIcon\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/language_assets/slide09_1466488574195.jpg\",\n" +
                "\"artifactUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/1467631155520_do_30013525.zip\",\n" +
                "\"code\":\"org.ekstep.numeracy.worksheet.520\",\n" +
                "\"collections\":[\n" +
                "\"do_30019820\"\n" +
                "],\n" +
                "\"concepts\":[\n" +
                "\"Num:C3:SC3:MC8\"\n" +
                "],\n" +
                "\"contentType\":\"Worksheet\",\n" +
                "\"copyright\":\"CC0\",\n" +
                "\"createdOn\":\"2016-06-21T05:48:58.983+0000\",\n" +
                "\"description\":\"This worksheet contains 10 questions from the multiplication table of 10. It is for students learning multiplication tables of 2-digit numbers. For each question, feedback is provided.\",\n" +
                "\"domain\":[\n" +
                "\"numeracy\"\n" +
                "],\n" +
                "\"downloadUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/do_30013525_1467631156173.ecar\",\n" +
                "\"es_metadata_id\":\"do_30013525\",\n" +
                "\"gradeLevel\":[\n" +
                "\"Grade 1\",\n" +
                "\"Grade 2\",\n" +
                "\"Grade 3\",\n" +
                "\"Grade 4\",\n" +
                "\"Grade 5\",\n" +
                "\"Other\"\n" +
                "],\n" +
                "\"graph_id\":\"domain\",\n" +
                "\"idealScreenDensity\":\"hdpi\",\n" +
                "\"idealScreenSize\":\"normal\",\n" +
                "\"identifier\":\"do_30013525\",\n" +
                "\"imageCredits\":[\n" +
                "\"ekstep\",\n" +
                "\"Parabal Singh\",\n" +
                "\"EkStep\"\n" +
                "],\n" +
                "\"language\":[\n" +
                "\"English\"\n" +
                "],\n" +
                "\"lastPublishedOn\":\"2016-07-04T11:19:16.489+0000\",\n" +
                "\"lastSubmittedOn\":\"2016-07-04T10:04:27.968+0000\",\n" +
                "\"lastUpdatedBy\":\"237\",\n" +
                "\"lastUpdatedOn\":\"2016-07-05T09:46:45.024+0000\",\n" +
                "\"license\":\"Creative Commons Attribution (CC BY)\",\n" +
                "\"mediaType\":\"content\",\n" +
                "\"mimeType\":\"application/vnd.ekstep.ecml-archive\",\n" +
                "\"name\":\"Multiplication - 10 Times Table\",\n" +
                "\"nodeType\":\"DATA_NODE\",\n" +
                "\"node_id\":13525,\n" +
                "\"objectType\":\"Content\",\n" +
                "\"optStatus\":\"Complete\",\n" +
                "\"os\":[\n" +
                "\"All\"\n" +
                "],\n" +
                "\"osId\":\"org.ekstep.quiz.app\",\n" +
                "\"owner\":\"Parabal Partap Singh\",\n" +
                "\"pkgVersion\":4,\n" +
                "\"portalOwner\":\"334\",\n" +
                "\"publisher\":\"\",\n" +
                "\"size\":1.507794e+06,\n" +
                "\"soundCredits\":[\n" +
                "\"ekstep\"\n" +
                "],\n" +
                "\"source\":\"EkStep\",\n" +
                "\"status\":\"Live\",\n" +
                "\"tags\":[\n" +
                "\"10 times table\",\n" +
                "\"multiplication table\",\n" +
                "\"multiplication\",\n" +
                "\"table of 10\",\n" +
                "\"multiply\",\n" +
                "\"times tables\",\n" +
                "\"multiplication tables\",\n" +
                "\"tables\"\n" +
                "],\n" +
                "\"visibility\":\"Default\"\n" +
                "},\n" +
                "{\n" +
                "\"ageGroup\":[\n" +
                "\"5-6\",\n" +
                "\"6-7\",\n" +
                "\"7-8\",\n" +
                "\"8-10\"\n" +
                "],\n" +
                "\"appIcon\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/language_assets/slide17_1466528472327.jpg\",\n" +
                "\"artifactUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/1467631311519_do_30014523.zip\",\n" +
                "\"code\":\"org.ekstep.numeracy.worksheet.554\",\n" +
                "\"collections\":[\n" +
                "\"do_30022230\"\n" +
                "],\n" +
                "\"concepts\":[\n" +
                "\"Num:C3:SC3:MC8\"\n" +
                "],\n" +
                "\"contentType\":\"Worksheet\",\n" +
                "\"copyright\":\"CC0\",\n" +
                "\"createdOn\":\"2016-06-21T16:34:33.401+0000\",\n" +
                "\"description\":\"This worksheet contains 10 questions from the multiplication table of 18. It is for students learning multiplication tables of 2-digit numbers. For each question, feedback is provided.\",\n" +
                "\"domain\":[\n" +
                "\"numeracy\"\n" +
                "],\n" +
                "\"downloadUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/do_30014523_1467631312097.ecar\",\n" +
                "\"es_metadata_id\":\"do_30014523\",\n" +
                "\"gradeLevel\":[\n" +
                "\"Grade 1\",\n" +
                "\"Grade 2\",\n" +
                "\"Grade 3\",\n" +
                "\"Grade 4\",\n" +
                "\"Grade 5\",\n" +
                "\"Other\"\n" +
                "],\n" +
                "\"graph_id\":\"domain\",\n" +
                "\"idealScreenDensity\":\"hdpi\",\n" +
                "\"idealScreenSize\":\"normal\",\n" +
                "\"identifier\":\"do_30014523\",\n" +
                "\"imageCredits\":[\n" +
                "\"ekstep\",\n" +
                "\"Parabal Singh\",\n" +
                "\"EkStep\"\n" +
                "],\n" +
                "\"language\":[\n" +
                "\"English\"\n" +
                "],\n" +
                "\"lastPublishedOn\":\"2016-07-04T11:21:52.431+0000\",\n" +
                "\"lastSubmittedOn\":\"2016-07-04T09:56:52.984+0000\",\n" +
                "\"lastUpdatedBy\":\"237\",\n" +
                "\"lastUpdatedOn\":\"2016-07-05T09:46:51.340+0000\",\n" +
                "\"license\":\"Creative Commons Attribution (CC BY)\",\n" +
                "\"mediaType\":\"content\",\n" +
                "\"mimeType\":\"application/vnd.ekstep.ecml-archive\",\n" +
                "\"name\":\"Multiplication - 18 Times Table\",\n" +
                "\"nodeType\":\"DATA_NODE\",\n" +
                "\"node_id\":14523,\n" +
                "\"objectType\":\"Content\",\n" +
                "\"optStatus\":\"Complete\",\n" +
                "\"os\":[\n" +
                "\"All\"\n" +
                "],\n" +
                "\"osId\":\"org.ekstep.quiz.app\",\n" +
                "\"owner\":\"Parabal Partap Singh\",\n" +
                "\"pkgVersion\":2,\n" +
                "\"portalOwner\":\"334\",\n" +
                "\"publisher\":\"\",\n" +
                "\"size\":1.508593e+06,\n" +
                "\"soundCredits\":[\n" +
                "\"ekstep\"\n" +
                "],\n" +
                "\"source\":\"EkStep\",\n" +
                "\"status\":\"Live\",\n" +
                "\"tags\":[\n" +
                "\"table of 18\",\n" +
                "\"multiplication table\",\n" +
                "\"multiplication\",\n" +
                "\"multiply\",\n" +
                "\"18 times table\",\n" +
                "\"tables\",\n" +
                "\"times table\",\n" +
                "\"multiplication tables\"\n" +
                "],\n" +
                "\"visibility\":\"Default\"\n" +
                "},\n" +
                "{\n" +
                "\"ageGroup\":[\n" +
                "\"5-6\",\n" +
                "\"6-7\",\n" +
                "\"7-8\",\n" +
                "\"8-10\"\n" +
                "],\n" +
                "\"artifactUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/1467385670957_do_30023208.zip\",\n" +
                "\"code\":\"org.ekstep.literacy.story.629\",\n" +
                "\"collaborators\":[\n" +
                "\"141\"\n" +
                "],\n" +
                "\"collections\":[\n" +
                "\"do_30033076\",\n" +
                "\"do_30022222\"\n" +
                "],\n" +
                "\"concepts\":[\n" +
                "\"LO7\"\n" +
                "],\n" +
                "\"contentType\":\"Story\",\n" +
                "\"copyright\":\"\",\n" +
                "\"createdOn\":\"2016-06-29T05:40:51.924+0000\",\n" +
                "\"description\":\"à¤¯à¤¹à¤¾à¤ à¤ªà¤° à¤†à¤ª 20 à¤›à¥‹à¤Ÿà¥‡ à¤…à¤¨à¥à¤šà¥à¤›à¥‡à¤¦ à¤ªà¤¾à¤à¤à¤—à¥‡ à¤œà¥‹ à¤¸à¥à¤¨à¥‡ à¤”à¤° à¤ªà¥à¥‡ à¤œà¤¾ à¤¸à¤•à¤¤à¥‡ à¤¹à¥ˆà¤‚à¥¤ à¤¹à¤° à¤…à¤¨à¥à¤šà¥à¤›à¥‡à¤¦ à¤•à¥‹ à¤…à¤ªà¤¨à¥€ à¤†à¤µà¤¾à¥› à¤®à¥‡à¤‚ à¤°à¥‡à¤•à¥‰à¤°à¥à¤¡ à¤•à¤°à¤•à¥‡ à¤¸à¥à¤¨à¤¾ à¤œà¤¾ à¤¸à¤•à¤¤à¤¾ à¤¹à¥ˆà¥¤ \",\n" +
                "\"domain\":[\n" +
                "\"literacy\"\n" +
                "],\n" +
                "\"downloadUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/do_30023208_1467385680915.ecar\",\n" +
                "\"es_metadata_id\":\"do_30023208\",\n" +
                "\"gradeLevel\":[\n" +
                "\"Kindergarten\",\n" +
                "\"Grade 1\",\n" +
                "\"Grade 2\",\n" +
                "\"Grade 3\",\n" +
                "\"Grade 4\",\n" +
                "\"Grade 5\",\n" +
                "\"Other\"\n" +
                "],\n" +
                "\"graph_id\":\"domain\",\n" +
                "\"idealScreenDensity\":\"hdpi\",\n" +
                "\"idealScreenSize\":\"normal\",\n" +
                "\"identifier\":\"do_30023208\",\n" +
                "\"language\":[\n" +
                "\"Hindi\"\n" +
                "],\n" +
                "\"lastPublishedOn\":\"2016-07-01T15:08:01.723+0000\",\n" +
                "\"lastSubmittedOn\":\"2016-06-29T05:53:50.150+0000\",\n" +
                "\"lastUpdatedBy\":\"141\",\n" +
                "\"lastUpdatedOn\":\"2016-07-05T09:47:08.236+0000\",\n" +
                "\"license\":\"Creative Commons Attribution (CC BY)\",\n" +
                "\"mediaType\":\"content\",\n" +
                "\"mimeType\":\"application/vnd.ekstep.ecml-archive\",\n" +
                "\"name\":\"PC Paragraph\",\n" +
                "\"nodeType\":\"DATA_NODE\",\n" +
                "\"node_id\":23208,\n" +
                "\"objectType\":\"Content\",\n" +
                "\"optStatus\":\"Complete\",\n" +
                "\"os\":[\n" +
                "\"All\"\n" +
                "],\n" +
                "\"osId\":\"org.ekstep.quiz.app\",\n" +
                "\"owner\":\"Pratham\",\n" +
                "\"pkgVersion\":5,\n" +
                "\"portalOwner\":\"286\",\n" +
                "\"publisher\":\"NA\",\n" +
                "\"size\":7.109925e+06,\n" +
                "\"source\":\"Pratham\",\n" +
                "\"status\":\"Live\",\n" +
                "\"tags\":[\n" +
                "\"decoding\",\n" +
                "\"read\",\n" +
                "\"hindi\",\n" +
                "\"word level\",\n" +
                "\"word\",\n" +
                "\"para\",\n" +
                "\"camal\",\n" +
                "\"para level\",\n" +
                "\"pratham\",\n" +
                "\"pratham camal\"\n" +
                "],\n" +
                "\"visibility\":\"Default\"\n" +
                "},\n" +
                "{\n" +
                "\"ageGroup\":[\n" +
                "\"5-6\",\n" +
                "\"6-7\",\n" +
                "\"7-8\"\n" +
                "],\n" +
                "\"appIcon\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/language_assets/slide06_1466487753334.jpg\",\n" +
                "\"artifactUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/1467631086978_do_30013511.zip\",\n" +
                "\"code\":\"org.ekstep.numeracy.worksheet.518\",\n" +
                "\"collections\":[\n" +
                "\"do_30019820\"\n" +
                "],\n" +
                "\"concepts\":[\n" +
                "\"Num:C3:SC3:MC8\"\n" +
                "],\n" +
                "\"contentType\":\"Worksheet\",\n" +
                "\"copyright\":\"CC0\",\n" +
                "\"createdOn\":\"2016-06-21T05:39:08.691+0000\",\n" +
                "\"description\":\"This worksheet contains 10 questions from the multiplication table of 7. It is for students learning multiplication tables of 2-digit numbers. For each question, feedback is provided.\",\n" +
                "\"domain\":[\n" +
                "\"numeracy\"\n" +
                "],\n" +
                "\"downloadUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/do_30013511_1467631087650.ecar\",\n" +
                "\"es_metadata_id\":\"do_30013511\",\n" +
                "\"gradeLevel\":[\n" +
                "\"Grade 1\",\n" +
                "\"Grade 2\",\n" +
                "\"Grade 3\",\n" +
                "\"Grade 4\",\n" +
                "\"Grade 5\",\n" +
                "\"Other\"\n" +
                "],\n" +
                "\"graph_id\":\"domain\",\n" +
                "\"idealScreenDensity\":\"hdpi\",\n" +
                "\"idealScreenSize\":\"normal\",\n" +
                "\"identifier\":\"do_30013511\",\n" +
                "\"imageCredits\":[\n" +
                "\"ekstep\",\n" +
                "\"Parabal Singh\",\n" +
                "\"EkStep\"\n" +
                "],\n" +
                "\"language\":[\n" +
                "\"English\"\n" +
                "],\n" +
                "\"lastPublishedOn\":\"2016-07-04T11:18:08.397+0000\",\n" +
                "\"lastSubmittedOn\":\"2016-07-04T10:02:55.234+0000\",\n" +
                "\"lastUpdatedBy\":\"237\",\n" +
                "\"lastUpdatedOn\":\"2016-07-05T09:46:43.420+0000\",\n" +
                "\"license\":\"Creative Commons Attribution (CC BY)\",\n" +
                "\"mediaType\":\"content\",\n" +
                "\"mimeType\":\"application/vnd.ekstep.ecml-archive\",\n" +
                "\"name\":\"Multiplication - 7 Times Table\",\n" +
                "\"nodeType\":\"DATA_NODE\",\n" +
                "\"node_id\":13511,\n" +
                "\"objectType\":\"Content\",\n" +
                "\"optStatus\":\"Complete\",\n" +
                "\"os\":[\n" +
                "\"All\"\n" +
                "],\n" +
                "\"osId\":\"org.ekstep.quiz.app\",\n" +
                "\"owner\":\"Parabal Partap Singh\",\n" +
                "\"pkgVersion\":3,\n" +
                "\"portalOwner\":\"334\",\n" +
                "\"publisher\":\"\",\n" +
                "\"size\":1.502993e+06,\n" +
                "\"soundCredits\":[\n" +
                "\"ekstep\"\n" +
                "],\n" +
                "\"source\":\"EkStep\",\n" +
                "\"status\":\"Live\",\n" +
                "\"tags\":[\n" +
                "\"times table\",\n" +
                "\"tables\",\n" +
                "\"multiplication\",\n" +
                "\"multiplication tables\",\n" +
                "\"multiplication table\",\n" +
                "\"multiply\",\n" +
                "\"7 times table\",\n" +
                "\"table of 7\"\n" +
                "],\n" +
                "\"visibility\":\"Default\"\n" +
                "},\n" +
                "{\n" +
                "\"ageGroup\":[\n" +
                "\"5-6\"\n" +
                "],\n" +
                "\"artifactUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/1467721835076_do_30030595.zip\",\n" +
                "\"code\":\"org.ekstep.numeracy.worksheet.674\",\n" +
                "\"collections\":[\n" +
                "\"do_30033076\",\n" +
                "\"do_30022222\"\n" +
                "],\n" +
                "\"concepts\":[\n" +
                "\"Num:C2:SC3:M8\"\n" +
                "],\n" +
                "\"contentType\":\"Worksheet\",\n" +
                "\"copyright\":\"\",\n" +
                "\"createdOn\":\"2016-07-01T11:50:16.641+0000\",\n" +
                "\"description\":\"Pratham Barahkadi wifi123\",\n" +
                "\"domain\":[\n" +
                "\"numeracy\"\n" +
                "],\n" +
                "\"downloadUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/do_30030595_1467721847723.ecar\",\n" +
                "\"es_metadata_id\":\"do_30030595\",\n" +
                "\"gradeLevel\":[\n" +
                "\"Kindergarten\",\n" +
                "\"Grade 1\",\n" +
                "\"Grade 2\",\n" +
                "\"Grade 3\",\n" +
                "\"Grade 4\",\n" +
                "\"Grade 5\",\n" +
                "\"Other\"\n" +
                "],\n" +
                "\"graph_id\":\"domain\",\n" +
                "\"idealScreenDensity\":\"hdpi\",\n" +
                "\"idealScreenSize\":\"normal\",\n" +
                "\"identifier\":\"do_30030595\",\n" +
                "\"imageCredits\":[\n" +
                "\"ekstep\"\n" +
                "],\n" +
                "\"language\":[\n" +
                "\"Hindi\"\n" +
                "],\n" +
                "\"lastPublishedOn\":\"2016-07-05T12:30:48.536+0000\",\n" +
                "\"lastSubmittedOn\":\"2016-07-01T11:57:41.796+0000\",\n" +
                "\"lastUpdatedBy\":\"286\",\n" +
                "\"lastUpdatedOn\":\"2016-07-05T12:30:48.618+0000\",\n" +
                "\"license\":\"Creative Commons Attribution (CC BY)\",\n" +
                "\"mediaType\":\"content\",\n" +
                "\"mimeType\":\"application/vnd.ekstep.ecml-archive\",\n" +
                "\"name\":\"PC Barahkhadi\",\n" +
                "\"nodeType\":\"DATA_NODE\",\n" +
                "\"node_id\":30595,\n" +
                "\"objectType\":\"Content\",\n" +
                "\"optStatus\":\"Complete\",\n" +
                "\"os\":[\n" +
                "\"All\"\n" +
                "],\n" +
                "\"osId\":\"org.ekstep.quiz.app\",\n" +
                "\"owner\":\"Harish S C\",\n" +
                "\"pkgVersion\":7,\n" +
                "\"portalOwner\":\"286\",\n" +
                "\"publisher\":\"\",\n" +
                "\"size\":6.962254e+06,\n" +
                "\"soundCredits\":[\n" +
                "\"ekstep\"\n" +
                "],\n" +
                "\"source\":\"\",\n" +
                "\"status\":\"Live\",\n" +
                "\"visibility\":\"Default\"\n" +
                "},\n" +
                "{\n" +
                "\"ageGroup\":[\n" +
                "\"5-6\",\n" +
                "\"6-7\",\n" +
                "\"7-8\",\n" +
                "\"8-10\"\n" +
                "],\n" +
                "\"appIcon\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/language_assets/Story5_1463740696341.png\",\n" +
                "\"artifactUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/1467611087192_domain_4083.zip\",\n" +
                "\"code\":\"org.ekstep.literacy.story.267\",\n" +
                "\"collaborators\":[\n" +
                "\"206\",\n" +
                "\"141\",\n" +
                "\"224\",\n" +
                "\"286\",\n" +
                "\"350\"\n" +
                "],\n" +
                "\"collections\":[\n" +
                "\"do_30033076\",\n" +
                "\"do_30022222\"\n" +
                "],\n" +
                "\"contentType\":\"Story\",\n" +
                "\"copyright\":\"\",\n" +
                "\"createdOn\":\"2016-05-02T10:03:16.029+0000\",\n" +
                "\"description\":\"à¤à¤• à¤•à¥à¤¤à¥à¤¤à¥‡ à¤•à¥‹ à¤°à¥‹à¤Ÿà¥€ à¤®à¤¿à¤²à¥€à¥¤ à¤ªà¥à¤¿à¤ à¤”à¤° à¤¦à¥‡à¤–à¤¿à¤¯à¥‡ à¤•à¥€ à¤‰à¤¸à¤¨à¥‡ à¤†à¤—à¥‡ à¤•à¥à¤¯à¤¾ à¤•à¤¿à¤¯à¤¾à¥¤ à¤•à¤¹à¤¾à¤¨à¥€ à¤‰à¤¨ à¤¬à¤šà¥à¤šà¥‹à¤‚ à¤•à¥‡ à¤²à¤¿à¤ à¤¹à¥ˆ à¤œà¥‹ à¤…à¤­à¥€ à¤µà¤¾à¤•à¥à¤¯ à¤ªà¥à¤¨à¤¾ à¤¶à¥à¤°à¥‚ à¤•à¤° à¤°à¤¹à¥‡ à¤¹à¥ˆà¤‚à¥¤ à¤•à¤¹à¤¾à¤¨à¥€ à¤•à¥‡ à¤•à¤ à¤¿à¤¨ à¤¶à¤¬à¥à¤¦ à¤•à¤¾ à¤…à¤­à¥à¤¯à¤¾à¤¸ à¤•à¤°à¤¾à¤¯à¤¾ à¤œà¤¾ à¤¸à¤•à¤¤à¤¾ à¤¹à¥ˆà¤‚à¥¤ à¤•à¤¹à¤¾à¤¨à¥€ à¤…à¤ªà¤¨à¥€ à¤†à¤µà¤¾à¥› à¤®à¥‡à¤‚ à¤°à¤¿à¤•à¥‰à¤°à¥à¤¡ à¤•à¤°à¥‡à¤‚ à¤”à¤° à¤¸à¥à¤¨à¥‡à¤‚à¥¤ à¤…à¤‚à¤¤ à¤®à¥‡à¤‚ à¤•à¥à¤› à¤¸à¤µà¤¾à¤² à¤¹à¥ˆà¤‚ à¤œà¥‹ à¤•à¤¹à¤¾à¤¨à¥€ à¤•à¥€ à¤¸à¤®à¤ à¤ªà¤°à¤–à¤¤à¥‡ à¤¹à¥ˆà¤‚à¥¤\",\n" +
                "\"developer\":\"EkStep\",\n" +
                "\"domain\":[\n" +
                "\"literacy\"\n" +
                "],\n" +
                "\"downloadUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/domain_4083_1467611093594.ecar\",\n" +
                "\"es_metadata_id\":\"domain_4083\",\n" +
                "\"genre\":[\n" +
                "\"Fiction\"\n" +
                "],\n" +
                "\"gradeLevel\":[\n" +
                "\"Kindergarten\",\n" +
                "\"Grade 1\",\n" +
                "\"Grade 2\",\n" +
                "\"Grade 3\",\n" +
                "\"Grade 4\",\n" +
                "\"Grade 5\",\n" +
                "\"Other\"\n" +
                "],\n" +
                "\"graph_id\":\"domain\",\n" +
                "\"idealScreenDensity\":\"hdpi\",\n" +
                "\"idealScreenSize\":\"normal\",\n" +
                "\"identifier\":\"domain_4083\",\n" +
                "\"imageCredits\":[\n" +
                "\"ekstep\",\n" +
                "\"323\",\n" +
                "\"Ekstep\",\n" +
                "\"External\"\n" +
                "],\n" +
                "\"language\":[\n" +
                "\"Hindi\"\n" +
                "],\n" +
                "\"lastPublishedOn\":\"2016-07-04T05:44:54.368+0000\",\n" +
                "\"lastSubmittedOn\":\"2016-07-04T04:31:16.477+0000\",\n" +
                "\"lastUpdatedBy\":\"141\",\n" +
                "\"lastUpdatedOn\":\"2016-07-05T09:46:33.972+0000\",\n" +
                "\"license\":\"Creative Commons Attribution (CC BY)\",\n" +
                "\"mediaType\":\"content\",\n" +
                "\"mimeType\":\"application/vnd.ekstep.ecml-archive\",\n" +
                "\"name\":\"à¤•à¥à¤¤à¥à¤¤à¤¾ à¤”à¤° à¤°à¥‹à¤Ÿà¥€\",\n" +
                "\"nodeType\":\"DATA_NODE\",\n" +
                "\"node_id\":4083,\n" +
                "\"objectType\":\"Content\",\n" +
                "\"optStatus\":\"Complete\",\n" +
                "\"os\":[\n" +
                "\"All\"\n" +
                "],\n" +
                "\"osId\":\"org.ekstep.quiz.app\",\n" +
                "\"owner\":\"Pratham\",\n" +
                "\"pkgVersion\":15,\n" +
                "\"portalOwner\":\"EkStep\",\n" +
                "\"publisher\":\"\",\n" +
                "\"size\":6.011039e+06,\n" +
                "\"soundCredits\":[\n" +
                "\"323\",\n" +
                "\"141\",\n" +
                "\"Aditya R\",\n" +
                "\"ekstep\",\n" +
                "\"Ekstep\",\n" +
                "\"EkStep\"\n" +
                "],\n" +
                "\"source\":\"Pratham.\",\n" +
                "\"status\":\"Live\",\n" +
                "\"tags\":[\n" +
                "\"Camp - 1 (Basic)\",\n" +
                "\"Story - 5\",\n" +
                "\"camal story #5\",\n" +
                "\"record and play\",\n" +
                "\"record\",\n" +
                "\"para level\",\n" +
                "\"story level\",\n" +
                "\"story\",\n" +
                "\"para and story level\",\n" +
                "\"kutha and roti\",\n" +
                "\"kutha aur roti\",\n" +
                "\"para\",\n" +
                "\"camal story\",\n" +
                "\"kutta\",\n" +
                "\"kutta aur roti\",\n" +
                "\"kutha\",\n" +
                "\"roti\",\n" +
                "\"hindi\",\n" +
                "\"camal\",\n" +
                "\"hindi stories\",\n" +
                "\"hindi story\",\n" +
                "\"pratham camal\",\n" +
                "\"pratham\",\n" +
                "\"40-60 words\"\n" +
                "],\n" +
                "\"visibility\":\"Default\"\n" +
                "},\n" +
                "{\n" +
                "\"ageGroup\":[\n" +
                "\"6-7\",\n" +
                "\"7-8\",\n" +
                "\"8-10\"\n" +
                "],\n" +
                "\"appIcon\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/language_assets/division_1466401982132.png\",\n" +
                "\"artifactUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/1467630869246_do_30013040.zip\",\n" +
                "\"code\":\"org.ekstep.numeracy.worksheet.469\",\n" +
                "\"concepts\":[\n" +
                "\"Num:C3:SC1:MC20\",\n" +
                "\"Num:C3:SC1:MC18\",\n" +
                "\"Num:C3:SC3\"\n" +
                "],\n" +
                "\"contentType\":\"Worksheet\",\n" +
                "\"copyright\":\"CC0\",\n" +
                "\"createdOn\":\"2016-06-20T05:49:54.016+0000\",\n" +
                "\"description\":\"à¤‡à¤¸ à¤µà¤°à¥à¤•à¤¶à¥€à¤Ÿ à¤®à¥‡à¤‚ à¤µà¤¿à¤­à¤¾à¤œà¤¨ à¤•à¥‡ 10 à¤¸à¤µà¤¾à¤² à¤¹à¥ˆà¤‚à¥¤ à¤¯à¤¹ à¤‰à¤¨ à¤¬à¤šà¥à¤šà¥‹à¤‚ à¤•à¥‡ à¤²à¤¿à¤ à¤¹à¥ˆ à¤œà¥‹ à¤…à¤­à¥€ à¤µà¤¿à¤­à¤¾à¤œà¤¨ à¤¸à¥€à¤– à¤°à¤¹à¥‡ à¤¹à¥ˆà¤‚à¥¤ à¤¹à¤° à¤¸à¤µà¤¾à¤² à¤•à¥‡ à¤¸à¤¹à¥€ à¤¯à¤¾ à¤—à¤²à¤¤ à¤¹à¥‹à¤¨à¥‡ à¤•à¥€ à¤œà¤¾à¤¨à¤•à¤¾à¤°à¥€ à¤¦à¥€ à¤œà¤¾à¤à¤—à¥€à¥¤\",\n" +
                "\"domain\":[\n" +
                "\"numeracy\"\n" +
                "],\n" +
                "\"downloadUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/do_30013040_1467630869906.ecar\",\n" +
                "\"es_metadata_id\":\"do_30013040\",\n" +
                "\"gradeLevel\":[\n" +
                "\"Grade 2\",\n" +
                "\"Grade 3\",\n" +
                "\"Grade 4\",\n" +
                "\"Grade 5\",\n" +
                "\"Other\"\n" +
                "],\n" +
                "\"graph_id\":\"domain\",\n" +
                "\"idealScreenDensity\":\"hdpi\",\n" +
                "\"idealScreenSize\":\"normal\",\n" +
                "\"identifier\":\"do_30013040\",\n" +
                "\"imageCredits\":[\n" +
                "\"ekstep\",\n" +
                "\"Parabal Singh\",\n" +
                "\"EkStep\"\n" +
                "],\n" +
                "\"language\":[\n" +
                "\"Hindi\"\n" +
                "],\n" +
                "\"lastPublishedOn\":\"2016-07-04T11:14:30.202+0000\",\n" +
                "\"lastSubmittedOn\":\"2016-07-04T10:04:56.258+0000\",\n" +
                "\"lastUpdatedBy\":\"237\",\n" +
                "\"lastUpdatedOn\":\"2016-07-05T09:46:31.141+0000\",\n" +
                "\"license\":\"Creative Commons Attribution (CC BY)\",\n" +
                "\"mediaType\":\"content\",\n" +
                "\"mimeType\":\"application/vnd.ekstep.ecml-archive\",\n" +
                "\"name\":\"à¤à¤•-à¤…à¤‚à¤• à¤•à¤¾ à¤µà¤¿à¤­à¤¾à¤œà¤¨ (Division)\",\n" +
                "\"nodeType\":\"DATA_NODE\",\n" +
                "\"node_id\":13040,\n" +
                "\"objectType\":\"Content\",\n" +
                "\"optStatus\":\"Complete\",\n" +
                "\"os\":[\n" +
                "\"All\"\n" +
                "],\n" +
                "\"osId\":\"org.ekstep.quiz.app\",\n" +
                "\"owner\":\"Parabal Partap Singh\",\n" +
                "\"pkgVersion\":2,\n" +
                "\"portalOwner\":\"334\",\n" +
                "\"publisher\":\"\",\n" +
                "\"size\":1.492166e+06,\n" +
                "\"soundCredits\":[\n" +
                "\"ekstep\"\n" +
                "],\n" +
                "\"source\":\"EkStep\",\n" +
                "\"status\":\"Live\",\n" +
                "\"tags\":[\n" +
                "\"ek ank ka vibhajan\",\n" +
                "\"vibhajan\",\n" +
                "\"division\",\n" +
                "\"divide\",\n" +
                "\"bhag\",\n" +
                "\"ek ank bhag\"\n" +
                "],\n" +
                "\"visibility\":\"Default\"\n" +
                "},\n" +
                "{\n" +
                "\"ageGroup\":[\n" +
                "\"5-6\",\n" +
                "\"6-7\",\n" +
                "\"7-8\"\n" +
                "],\n" +
                "\"appIcon\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/language_assets/slide03_1466487351208.jpg\",\n" +
                "\"artifactUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/1467630936844_do_30013504.zip\",\n" +
                "\"code\":\"org.ekstep.numeracy.worksheet.514\",\n" +
                "\"collections\":[\n" +
                "\"do_30019820\"\n" +
                "],\n" +
                "\"concepts\":[\n" +
                "\"Num:C3:SC3:MC8\"\n" +
                "],\n" +
                "\"contentType\":\"Worksheet\",\n" +
                "\"copyright\":\"CC0\",\n" +
                "\"createdOn\":\"2016-06-21T05:34:59.036+0000\",\n" +
                "\"description\":\"This worksheet contains 10 questions from the multiplication table of 4. It is for students learning multiplication tables of 2-digit numbers. For each question, feedback is provided.\",\n" +
                "\"domain\":[\n" +
                "\"numeracy\"\n" +
                "],\n" +
                "\"downloadUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/do_30013504_1467630939408.ecar\",\n" +
                "\"es_metadata_id\":\"do_30013504\",\n" +
                "\"gradeLevel\":[\n" +
                "\"Grade 1\",\n" +
                "\"Grade 2\",\n" +
                "\"Grade 3\",\n" +
                "\"Grade 4\",\n" +
                "\"Grade 5\",\n" +
                "\"Other\"\n" +
                "],\n" +
                "\"graph_id\":\"domain\",\n" +
                "\"idealScreenDensity\":\"hdpi\",\n" +
                "\"idealScreenSize\":\"normal\",\n" +
                "\"identifier\":\"do_30013504\",\n" +
                "\"imageCredits\":[\n" +
                "\"ekstep\",\n" +
                "\"Parabal Singh\",\n" +
                "\"EkStep\"\n" +
                "],\n" +
                "\"language\":[\n" +
                "\"English\"\n" +
                "],\n" +
                "\"lastPublishedOn\":\"2016-07-04T11:15:39.700+0000\",\n" +
                "\"lastSubmittedOn\":\"2016-07-04T10:01:22.554+0000\",\n" +
                "\"lastUpdatedBy\":\"237\",\n" +
                "\"lastUpdatedOn\":\"2016-07-05T09:46:41.676+0000\",\n" +
                "\"license\":\"Creative Commons Attribution (CC BY)\",\n" +
                "\"mediaType\":\"content\",\n" +
                "\"mimeType\":\"application/vnd.ekstep.ecml-archive\",\n" +
                "\"name\":\"Multiplication - 4 Times Table\",\n" +
                "\"nodeType\":\"DATA_NODE\",\n" +
                "\"node_id\":13504,\n" +
                "\"objectType\":\"Content\",\n" +
                "\"optStatus\":\"Complete\",\n" +
                "\"os\":[\n" +
                "\"All\"\n" +
                "],\n" +
                "\"osId\":\"org.ekstep.quiz.app\",\n" +
                "\"owner\":\"Parabal Partap Singh\",\n" +
                "\"pkgVersion\":4,\n" +
                "\"portalOwner\":\"334\",\n" +
                "\"publisher\":\"\",\n" +
                "\"size\":1.503255e+06,\n" +
                "\"soundCredits\":[\n" +
                "\"ekstep\"\n" +
                "],\n" +
                "\"source\":\"EkStep\",\n" +
                "\"status\":\"Live\",\n" +
                "\"tags\":[\n" +
                "\"table of 4\",\n" +
                "\"4 times table\",\n" +
                "\"multiplication tables\",\n" +
                "\"multiplication\",\n" +
                "\"tables\",\n" +
                "\"multiplication table\",\n" +
                "\"multiply\"\n" +
                "],\n" +
                "\"visibility\":\"Default\"\n" +
                "},\n" +
                "{\n" +
                "\"ageGroup\":[\n" +
                "\"5-6\"\n" +
                "],\n" +
                "\"appIcon\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/download_239_1469426013_1469426067255.thumb.jpg\",\n" +
                "\"artifactUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/1469426221645_do_20043780.zip\",\n" +
                "\"code\":\"org.ekstep.literacy.story.1370\",\n" +
                "\"contentType\":\"Story\",\n" +
                "\"createdOn\":\"2016-07-25T05:53:31.086+0000\",\n" +
                "\"description\":\"TEST\",\n" +
                "\"domain\":[\n" +
                "\"literacy\",\n" +
                "\"numeracy\"\n" +
                "],\n" +
                "\"downloadUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/sqs-5522_1469426222102_do_20043780.ecar\",\n" +
                "\"es_metadata_id\":\"do_20043780\",\n" +
                "\"gradeLevel\":[\n" +
                "\"Kindergarten\",\n" +
                "\"Grade 1\",\n" +
                "\"Grade 2\",\n" +
                "\"Grade 3\",\n" +
                "\"Grade 4\",\n" +
                "\"Grade 5\",\n" +
                "\"Other\"\n" +
                "],\n" +
                "\"graph_id\":\"domain\",\n" +
                "\"idealScreenDensity\":\"hdpi\",\n" +
                "\"idealScreenSize\":\"normal\",\n" +
                "\"identifier\":\"do_20043780\",\n" +
                "\"imageCredits\":[\n" +
                "\"ekstep\"\n" +
                "],\n" +
                "\"language\":[\n" +
                "\"Marathi\"\n" +
                "],\n" +
                "\"lastPublishedOn\":\"2016-07-25T05:57:02.490+0000\",\n" +
                "\"lastSubmittedOn\":\"2016-07-25T05:54:27.391+0000\",\n" +
                "\"lastUpdatedBy\":\"382\",\n" +
                "\"lastUpdatedOn\":\"2016-07-25T05:57:02.649+0000\",\n" +
                "\"license\":\"Creative Commons Attribution (CC BY)\",\n" +
                "\"mediaType\":\"content\",\n" +
                "\"mimeType\":\"application/vnd.ekstep.ecml-archive\",\n" +
                "\"name\":\"SQS 5522\",\n" +
                "\"nodeType\":\"DATA_NODE\",\n" +
                "\"node_id\":43780,\n" +
                "\"objectType\":\"Content\",\n" +
                "\"os\":[\n" +
                "\"All\"\n" +
                "],\n" +
                "\"osId\":\"org.ekstep.quiz.app\",\n" +
                "\"owner\":\"External Testing vendor\",\n" +
                "\"pkgVersion\":1,\n" +
                "\"portalOwner\":\"239\",\n" +
                "\"posterImage\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/language_assets/download_239_1469426013_1469426067255.jpg\",\n" +
                "\"size\":1.276963e+06,\n" +
                "\"soundCredits\":[\n" +
                "\"ekstep\"\n" +
                "],\n" +
                "\"status\":\"Live\",\n" +
                "\"visibility\":\"Default\"\n" +
                "}\n" +
                "]\n" +
                "},\n" +
                "{\n" +
                "\"display\":{\n" +
                "\"name\":{\n" +
                "\"en\":\"Newest\",\n" +
                "\"hn\":\"à¤¸à¤¬à¤¸à¥‡ à¤¨à¤\"\n" +
                "}\n" +
                "},\n" +
                "\"search\":{\n" +
                "\"query\":\"\",\n" +
                "\"limit\":10,\n" +
                "\"sort_by\":{\n" +
                "\"lastUpdatedOn\":\"desc\"\n" +
                "},\n" +
                "\"filters\":{\n" +
                "\"contentType\":[\n" +
                "\"Story\",\n" +
                "\"Worksheet\",\n" +
                "\"Collection\"\n" +
                "],\n" +
                "\"objectType\":[\n" +
                "\"Content\"\n" +
                "],\n" +
                "\"status\":[\n" +
                "\"Live\"\n" +
                "]\n" +
                "},\n" +
                "\"facets\":[\n" +
                "\"contentType\",\n" +
                "\"domain\",\n" +
                "\"ageGroup\",\n" +
                "\"language\",\n" +
                "\"gradeLevel\"\n" +
                "]\n" +
                "},\n" +
                "\"recommend\":null,\n" +
                "\"contents\":[\n" +
                "{\n" +
                "\"ageGroup\":[\n" +
                "\"5-6\"\n" +
                "],\n" +
                "\"appIcon\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/content_350_1469102933_1469102979779.thumb.jpg\",\n" +
                "\"artifactUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/htmlcontent_1470817564412.zip\",\n" +
                "\"code\":\"org.ekstep.numeracy.story.1347\",\n" +
                "\"contentType\":\"Worksheet\",\n" +
                "\"createdOn\":\"2016-07-21T12:08:56.170+0000\",\n" +
                "\"description\":\"HTML CONTENT VSV1\",\n" +
                "\"domain\":[\n" +
                "\"numeracy\"\n" +
                "],\n" +
                "\"downloadUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/html-content-vsv2_1470817569714_do_20043647.ecar\",\n" +
                "\"es_metadata_id\":\"do_20043647\",\n" +
                "\"gradeLevel\":[\n" +
                "\"Kindergarten\",\n" +
                "\"Grade 1\",\n" +
                "\"Grade 2\",\n" +
                "\"Grade 3\",\n" +
                "\"Grade 4\",\n" +
                "\"Grade 5\",\n" +
                "\"Other\"\n" +
                "],\n" +
                "\"graph_id\":\"domain\",\n" +
                "\"idealScreenDensity\":\"hdpi\",\n" +
                "\"idealScreenSize\":\"normal\",\n" +
                "\"identifier\":\"do_20043647\",\n" +
                "\"language\":[\n" +
                "\"English\"\n" +
                "],\n" +
                "\"lastPublishedOn\":\"2016-08-10T08:26:09.989+0000\",\n" +
                "\"lastSubmittedOn\":\"2016-08-04T10:24:37.529+0000\",\n" +
                "\"lastUpdatedBy\":\"350\",\n" +
                "\"lastUpdatedOn\":\"2016-08-10T08:26:10.074+0000\",\n" +
                "\"license\":\"Creative Commons Attribution (CC BY)\",\n" +
                "\"mediaType\":\"content\",\n" +
                "\"mimeType\":\"application/vnd.ekstep.html-archive\",\n" +
                "\"name\":\"HTML CONTENT VSV2\",\n" +
                "\"nodeType\":\"DATA_NODE\",\n" +
                "\"node_id\":43647,\n" +
                "\"objectType\":\"Content\",\n" +
                "\"os\":[\n" +
                "\"All\"\n" +
                "],\n" +
                "\"osId\":\"org.ekstep.quiz.app\",\n" +
                "\"owner\":\"Vinu Kumar\",\n" +
                "\"pkgVersion\":16,\n" +
                "\"portalOwner\":\"350\",\n" +
                "\"posterImage\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/language_assets/content_350_1469102933_1469102979779.jpg\",\n" +
                "\"size\":107730,\n" +
                "\"status\":\"Live\",\n" +
                "\"visibility\":\"Default\"\n" +
                "},\n" +
                "{\n" +
                "\"ageGroup\":[\n" +
                "\"5-6\"\n" +
                "],\n" +
                "\"appIcon\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/adjective_347_1469089047_1469089093674.thumb.png\",\n" +
                "\"artifactUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/isaksham_347_1470812028_1470812110480.zip\",\n" +
                "\"code\":\"org.ekstep.literacy.worksheet.1338\",\n" +
                "\"concepts\":[\n" +
                "\"LO52\"\n" +
                "],\n" +
                "\"contentType\":\"Worksheet\",\n" +
                "\"createdOn\":\"2016-07-21T08:17:26.217+0000\",\n" +
                "\"description\":\"test_isaksham\",\n" +
                "\"domain\":[\n" +
                "\"literacy\"\n" +
                "],\n" +
                "\"downloadUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/test_isaksham_1470812392508_do_20043586.ecar\",\n" +
                "\"es_metadata_id\":\"do_20043586\",\n" +
                "\"gradeLevel\":[\n" +
                "\"Kindergarten\",\n" +
                "\"Grade 1\",\n" +
                "\"Grade 2\",\n" +
                "\"Grade 3\",\n" +
                "\"Grade 4\",\n" +
                "\"Grade 5\",\n" +
                "\"Other\"\n" +
                "],\n" +
                "\"graph_id\":\"domain\",\n" +
                "\"idealScreenDensity\":\"hdpi\",\n" +
                "\"idealScreenSize\":\"normal\",\n" +
                "\"identifier\":\"do_20043586\",\n" +
                "\"language\":[\n" +
                "\"English\"\n" +
                "],\n" +
                "\"lastPublishedOn\":\"2016-08-10T06:59:53.491+0000\",\n" +
                "\"lastSubmittedOn\":\"2016-08-10T06:55:24.096+0000\",\n" +
                "\"lastUpdatedBy\":\"331\",\n" +
                "\"lastUpdatedOn\":\"2016-08-10T06:59:53.581+0000\",\n" +
                "\"license\":\"Creative Commons Attribution (CC BY)\",\n" +
                "\"mediaType\":\"content\",\n" +
                "\"mimeType\":\"application/vnd.ekstep.ecml-archive\",\n" +
                "\"name\":\"test_isaksham\",\n" +
                "\"nodeType\":\"DATA_NODE\",\n" +
                "\"node_id\":43586,\n" +
                "\"objectType\":\"Content\",\n" +
                "\"os\":[\n" +
                "\"All\"\n" +
                "],\n" +
                "\"osId\":\"org.ekstep.quiz.app\",\n" +
                "\"owner\":\"Debasis Singh\",\n" +
                "\"pkgVersion\":2,\n" +
                "\"portalOwner\":\"347\",\n" +
                "\"posterImage\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/language_assets/adjective_347_1469089047_1469089093674.png\",\n" +
                "\"size\":5.643203e+06,\n" +
                "\"status\":\"Live\",\n" +
                "\"visibility\":\"Default\"\n" +
                "},\n" +
                "{\n" +
                "\"ageGroup\":[\n" +
                "\"5-6\"\n" +
                "],\n" +
                "\"appIcon\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/adjective_347_1468576675_1468576711302.thumb.png\",\n" +
                "\"artifactUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/ginti_347_1470812147_1470812229994.zip\",\n" +
                "\"code\":\"org.ekstep.literacy.story.1203\",\n" +
                "\"concepts\":[\n" +
                "\"LO52\"\n" +
                "],\n" +
                "\"contentType\":\"Story\",\n" +
                "\"createdOn\":\"2016-07-15T05:56:44.478+0000\",\n" +
                "\"description\":\"test_ginti\",\n" +
                "\"domain\":[\n" +
                "\"literacy\"\n" +
                "],\n" +
                "\"downloadUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/test_ginti_1470812387496_do_20043348.ecar\",\n" +
                "\"es_metadata_id\":\"do_20043348\",\n" +
                "\"gradeLevel\":[\n" +
                "\"Kindergarten\",\n" +
                "\"Grade 1\",\n" +
                "\"Grade 2\",\n" +
                "\"Grade 3\",\n" +
                "\"Grade 4\",\n" +
                "\"Grade 5\",\n" +
                "\"Other\"\n" +
                "],\n" +
                "\"graph_id\":\"domain\",\n" +
                "\"idealScreenDensity\":\"hdpi\",\n" +
                "\"idealScreenSize\":\"normal\",\n" +
                "\"identifier\":\"do_20043348\",\n" +
                "\"language\":[\n" +
                "\"English\"\n" +
                "],\n" +
                "\"lastPublishedOn\":\"2016-08-10T06:59:47.829+0000\",\n" +
                "\"lastSubmittedOn\":\"2016-08-10T06:57:35.083+0000\",\n" +
                "\"lastUpdatedBy\":\"331\",\n" +
                "\"lastUpdatedOn\":\"2016-08-10T06:59:47.974+0000\",\n" +
                "\"license\":\"Creative Commons Attribution (CC BY)\",\n" +
                "\"mediaType\":\"content\",\n" +
                "\"mimeType\":\"application/vnd.ekstep.ecml-archive\",\n" +
                "\"name\":\"test_ginti\",\n" +
                "\"nodeType\":\"DATA_NODE\",\n" +
                "\"node_id\":43348,\n" +
                "\"objectType\":\"Content\",\n" +
                "\"os\":[\n" +
                "\"All\"\n" +
                "],\n" +
                "\"osId\":\"org.ekstep.quiz.app\",\n" +
                "\"owner\":\"Debasis Singh\",\n" +
                "\"pkgVersion\":11,\n" +
                "\"portalOwner\":\"347\",\n" +
                "\"posterImage\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/language_assets/adjective_347_1468576675_1468576711302.png\",\n" +
                "\"size\":1.384829e+06,\n" +
                "\"status\":\"Live\",\n" +
                "\"visibility\":\"Default\"\n" +
                "},\n" +
                "{\n" +
                "\"ageGroup\":[\n" +
                "\"5-6\"\n" +
                "],\n" +
                "\"appIcon\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/adjective_347_1468566136_1468566172028.thumb.png\",\n" +
                "\"artifactUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/pratham1_347_1470811602_1470811684833.zip\",\n" +
                "\"code\":\"org.ekstep.literacy.story.1211\",\n" +
                "\"contentType\":\"Story\",\n" +
                "\"createdOn\":\"2016-07-15T06:56:57.362+0000\",\n" +
                "\"description\":\"test_prathamstoriesfirstday\",\n" +
                "\"domain\":[\n" +
                "\"literacy\"\n" +
                "],\n" +
                "\"downloadUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/test_prathamstoriesfirstday_1470811747793_do_20043357.ecar\",\n" +
                "\"es_metadata_id\":\"do_20043357\",\n" +
                "\"gradeLevel\":[\n" +
                "\"Kindergarten\",\n" +
                "\"Grade 1\",\n" +
                "\"Grade 2\",\n" +
                "\"Grade 3\",\n" +
                "\"Grade 4\",\n" +
                "\"Grade 5\",\n" +
                "\"Other\"\n" +
                "],\n" +
                "\"graph_id\":\"domain\",\n" +
                "\"idealScreenDensity\":\"hdpi\",\n" +
                "\"idealScreenSize\":\"normal\",\n" +
                "\"identifier\":\"do_20043357\",\n" +
                "\"language\":[\n" +
                "\"English\"\n" +
                "],\n" +
                "\"lastPublishedOn\":\"2016-08-10T06:49:09.440+0000\",\n" +
                "\"lastSubmittedOn\":\"2016-08-10T06:48:19.310+0000\",\n" +
                "\"lastUpdatedBy\":\"400\",\n" +
                "\"lastUpdatedOn\":\"2016-08-10T06:49:09.596+0000\",\n" +
                "\"license\":\"Creative Commons Attribution (CC BY)\",\n" +
                "\"mediaType\":\"content\",\n" +
                "\"mimeType\":\"application/vnd.ekstep.ecml-archive\",\n" +
                "\"name\":\"test_prathamstoriesfirstday\",\n" +
                "\"nodeType\":\"DATA_NODE\",\n" +
                "\"node_id\":43357,\n" +
                "\"objectType\":\"Content\",\n" +
                "\"os\":[\n" +
                "\"All\"\n" +
                "],\n" +
                "\"osId\":\"org.ekstep.quiz.app\",\n" +
                "\"owner\":\"Debasis Singh\",\n" +
                "\"pkgVersion\":4,\n" +
                "\"portalOwner\":\"347\",\n" +
                "\"posterImage\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/language_assets/adjective_347_1468566136_1468566172028.png\",\n" +
                "\"size\":1.190686e+07,\n" +
                "\"status\":\"Live\",\n" +
                "\"visibility\":\"Default\"\n" +
                "},\n" +
                "{\n" +
                "\"ageGroup\":[\n" +
                "\"5-6\"\n" +
                "],\n" +
                "\"appIcon\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/adjective_347_1468566291_1468566326979.thumb.png\",\n" +
                "\"artifactUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/pratham2_347_1470811563_1470811645341.zip\",\n" +
                "\"code\":\"org.ekstep.literacy.story.1212\",\n" +
                "\"concepts\":[\n" +
                "\"LO52\"\n" +
                "],\n" +
                "\"contentType\":\"Story\",\n" +
                "\"createdOn\":\"2016-07-15T07:04:09.613+0000\",\n" +
                "\"description\":\"test_prathamstoriessecondday\",\n" +
                "\"domain\":[\n" +
                "\"literacy\"\n" +
                "],\n" +
                "\"downloadUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/test_prathamstoriessecondday_1470811717805_do_20043358.ecar\",\n" +
                "\"es_metadata_id\":\"do_20043358\",\n" +
                "\"gradeLevel\":[\n" +
                "\"Kindergarten\",\n" +
                "\"Grade 1\",\n" +
                "\"Grade 2\",\n" +
                "\"Grade 3\",\n" +
                "\"Grade 4\",\n" +
                "\"Grade 5\",\n" +
                "\"Other\"\n" +
                "],\n" +
                "\"graph_id\":\"domain\",\n" +
                "\"idealScreenDensity\":\"hdpi\",\n" +
                "\"idealScreenSize\":\"normal\",\n" +
                "\"identifier\":\"do_20043358\",\n" +
                "\"language\":[\n" +
                "\"English\"\n" +
                "],\n" +
                "\"lastPublishedOn\":\"2016-08-10T06:48:39.347+0000\",\n" +
                "\"lastSubmittedOn\":\"2016-08-10T06:47:42.358+0000\",\n" +
                "\"lastUpdatedBy\":\"400\",\n" +
                "\"lastUpdatedOn\":\"2016-08-10T06:48:39.410+0000\",\n" +
                "\"license\":\"Creative Commons Attribution (CC BY)\",\n" +
                "\"mediaType\":\"content\",\n" +
                "\"mimeType\":\"application/vnd.ekstep.ecml-archive\",\n" +
                "\"name\":\"test_prathamstoriessecondday\",\n" +
                "\"nodeType\":\"DATA_NODE\",\n" +
                "\"node_id\":43358,\n" +
                "\"objectType\":\"Content\",\n" +
                "\"os\":[\n" +
                "\"All\"\n" +
                "],\n" +
                "\"osId\":\"org.ekstep.quiz.app\",\n" +
                "\"owner\":\"Debasis Singh\",\n" +
                "\"pkgVersion\":3,\n" +
                "\"portalOwner\":\"347\",\n" +
                "\"posterImage\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/language_assets/adjective_347_1468566291_1468566326979.png\",\n" +
                "\"size\":1.0119578e+07,\n" +
                "\"status\":\"Live\",\n" +
                "\"visibility\":\"Default\"\n" +
                "},\n" +
                "{\n" +
                "\"ageGroup\":[\n" +
                "\"5-6\"\n" +
                "],\n" +
                "\"appIcon\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/adjective_347_1468567038_1468567073913.thumb.png\",\n" +
                "\"artifactUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/prathamstory6_347_1470808253_1470808335246.zip\",\n" +
                "\"code\":\"org.ekstep.literacy.story.1221\",\n" +
                "\"concepts\":[\n" +
                "\"LO52\"\n" +
                "],\n" +
                "\"contentType\":\"Story\",\n" +
                "\"createdOn\":\"2016-07-15T07:16:59.113+0000\",\n" +
                "\"description\":\"test_prathamstoriessixthday\",\n" +
                "\"domain\":[\n" +
                "\"literacy\"\n" +
                "],\n" +
                "\"downloadUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/test_prathamstoriessixthday_1470811710161_do_20043367.ecar\",\n" +
                "\"es_metadata_id\":\"do_20043367\",\n" +
                "\"gradeLevel\":[\n" +
                "\"Kindergarten\",\n" +
                "\"Grade 1\",\n" +
                "\"Grade 2\",\n" +
                "\"Grade 3\",\n" +
                "\"Grade 4\",\n" +
                "\"Grade 5\",\n" +
                "\"Other\"\n" +
                "],\n" +
                "\"graph_id\":\"domain\",\n" +
                "\"idealScreenDensity\":\"hdpi\",\n" +
                "\"idealScreenSize\":\"normal\",\n" +
                "\"identifier\":\"do_20043367\",\n" +
                "\"language\":[\n" +
                "\"English\"\n" +
                "],\n" +
                "\"lastPublishedOn\":\"2016-08-10T06:48:31.107+0000\",\n" +
                "\"lastSubmittedOn\":\"2016-08-10T05:52:34.123+0000\",\n" +
                "\"lastUpdatedBy\":\"400\",\n" +
                "\"lastUpdatedOn\":\"2016-08-10T06:48:31.162+0000\",\n" +
                "\"license\":\"Creative Commons Attribution (CC BY)\",\n" +
                "\"mediaType\":\"content\",\n" +
                "\"mimeType\":\"application/vnd.ekstep.ecml-archive\",\n" +
                "\"name\":\"test_prathamstoriessixthday\",\n" +
                "\"nodeType\":\"DATA_NODE\",\n" +
                "\"node_id\":43367,\n" +
                "\"objectType\":\"Content\",\n" +
                "\"os\":[\n" +
                "\"All\"\n" +
                "],\n" +
                "\"osId\":\"org.ekstep.quiz.app\",\n" +
                "\"owner\":\"Debasis Singh\",\n" +
                "\"pkgVersion\":3,\n" +
                "\"portalOwner\":\"347\",\n" +
                "\"posterImage\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/language_assets/adjective_347_1468567038_1468567073913.png\",\n" +
                "\"size\":5.747611e+06,\n" +
                "\"status\":\"Live\",\n" +
                "\"visibility\":\"Default\"\n" +
                "},\n" +
                "{\n" +
                "\"ageGroup\":[\n" +
                "\"5-6\"\n" +
                "],\n" +
                "\"appIcon\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/adjective_347_1468566837_1468566872504.thumb.png\",\n" +
                "\"artifactUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/prathamstory4_347_1470808206_1470808289000.zip\",\n" +
                "\"code\":\"org.ekstep.literacy.story.1218\",\n" +
                "\"concepts\":[\n" +
                "\"LO52\"\n" +
                "],\n" +
                "\"contentType\":\"Story\",\n" +
                "\"createdOn\":\"2016-07-15T07:13:47.488+0000\",\n" +
                "\"description\":\"test_prathamstoriesfourthday\",\n" +
                "\"domain\":[\n" +
                "\"literacy\"\n" +
                "],\n" +
                "\"downloadUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/test_prathamstoriesfourthday_1470811697862_do_20043364.ecar\",\n" +
                "\"es_metadata_id\":\"do_20043364\",\n" +
                "\"gradeLevel\":[\n" +
                "\"Kindergarten\",\n" +
                "\"Grade 1\",\n" +
                "\"Grade 2\",\n" +
                "\"Grade 3\",\n" +
                "\"Grade 4\",\n" +
                "\"Grade 5\",\n" +
                "\"Other\"\n" +
                "],\n" +
                "\"graph_id\":\"domain\",\n" +
                "\"idealScreenDensity\":\"hdpi\",\n" +
                "\"idealScreenSize\":\"normal\",\n" +
                "\"identifier\":\"do_20043364\",\n" +
                "\"language\":[\n" +
                "\"English\"\n" +
                "],\n" +
                "\"lastPublishedOn\":\"2016-08-10T06:48:18.798+0000\",\n" +
                "\"lastSubmittedOn\":\"2016-08-10T05:51:44.237+0000\",\n" +
                "\"lastUpdatedBy\":\"400\",\n" +
                "\"lastUpdatedOn\":\"2016-08-10T06:48:18.955+0000\",\n" +
                "\"license\":\"Creative Commons Attribution (CC BY)\",\n" +
                "\"mediaType\":\"content\",\n" +
                "\"mimeType\":\"application/vnd.ekstep.ecml-archive\",\n" +
                "\"name\":\"test_prathamstoriesfourthday\",\n" +
                "\"nodeType\":\"DATA_NODE\",\n" +
                "\"node_id\":43364,\n" +
                "\"objectType\":\"Content\",\n" +
                "\"os\":[\n" +
                "\"All\"\n" +
                "],\n" +
                "\"osId\":\"org.ekstep.quiz.app\",\n" +
                "\"owner\":\"Debasis Singh\",\n" +
                "\"pkgVersion\":3,\n" +
                "\"portalOwner\":\"347\",\n" +
                "\"posterImage\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/language_assets/adjective_347_1468566837_1468566872504.png\",\n" +
                "\"size\":5.376783e+06,\n" +
                "\"status\":\"Live\",\n" +
                "\"visibility\":\"Default\"\n" +
                "},\n" +
                "{\n" +
                "\"ageGroup\":[\n" +
                "\"5-6\"\n" +
                "],\n" +
                "\"appIcon\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/adjective_347_1469089491_1469089538117.thumb.png\",\n" +
                "\"artifactUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/haircut_story_347_1470811407_1470811489840.zip\",\n" +
                "\"code\":\"org.ekstep.literacy.story.1339\",\n" +
                "\"concepts\":[\n" +
                "\"LO52\"\n" +
                "],\n" +
                "\"contentType\":\"Story\",\n" +
                "\"createdOn\":\"2016-07-21T08:24:28.840+0000\",\n" +
                "\"description\":\"test_annualhaircutday\",\n" +
                "\"domain\":[\n" +
                "\"literacy\"\n" +
                "],\n" +
                "\"downloadUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/test_annualhaircutday_1470811621637_do_20043587.ecar\",\n" +
                "\"es_metadata_id\":\"do_20043587\",\n" +
                "\"gradeLevel\":[\n" +
                "\"Kindergarten\",\n" +
                "\"Grade 1\",\n" +
                "\"Grade 2\",\n" +
                "\"Grade 3\",\n" +
                "\"Grade 4\",\n" +
                "\"Grade 5\",\n" +
                "\"Other\"\n" +
                "],\n" +
                "\"graph_id\":\"domain\",\n" +
                "\"idealScreenDensity\":\"hdpi\",\n" +
                "\"idealScreenSize\":\"normal\",\n" +
                "\"identifier\":\"do_20043587\",\n" +
                "\"language\":[\n" +
                "\"English\"\n" +
                "],\n" +
                "\"lastPublishedOn\":\"2016-08-10T06:47:05.715+0000\",\n" +
                "\"lastSubmittedOn\":\"2016-08-10T06:45:12.102+0000\",\n" +
                "\"lastUpdatedBy\":\"400\",\n" +
                "\"lastUpdatedOn\":\"2016-08-10T06:47:06.102+0000\",\n" +
                "\"license\":\"Creative Commons Attribution (CC BY)\",\n" +
                "\"mediaType\":\"content\",\n" +
                "\"mimeType\":\"application/vnd.ekstep.ecml-archive\",\n" +
                "\"name\":\"test_annualhaircutday\",\n" +
                "\"nodeType\":\"DATA_NODE\",\n" +
                "\"node_id\":43587,\n" +
                "\"objectType\":\"Content\",\n" +
                "\"os\":[\n" +
                "\"All\"\n" +
                "],\n" +
                "\"osId\":\"org.ekstep.quiz.app\",\n" +
                "\"owner\":\"Debasis Singh\",\n" +
                "\"pkgVersion\":8,\n" +
                "\"portalOwner\":\"347\",\n" +
                "\"posterImage\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/language_assets/adjective_347_1469089491_1469089538117.png\",\n" +
                "\"size\":2.1378572e+07,\n" +
                "\"status\":\"Live\",\n" +
                "\"visibility\":\"Default\"\n" +
                "},\n" +
                "{\n" +
                "\"ageGroup\":[\n" +
                "\"5-6\"\n" +
                "],\n" +
                "\"appIcon\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/1455104397576flowersl.thumb.png\",\n" +
                "\"artifactUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/esl_part4_347_1470744638_1470744719032.zip\",\n" +
                "\"code\":\"org.ekstep.literacy.story.1534\",\n" +
                "\"contentType\":\"Story\",\n" +
                "\"createdOn\":\"2016-08-03T08:37:02.937+0000\",\n" +
                "\"description\":\"Test_eslpart4\",\n" +
                "\"domain\":[\n" +
                "\"literacy\"\n" +
                "],\n" +
                "\"downloadUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/test_eslpart4_1470744887132_do_20044433.ecar\",\n" +
                "\"es_metadata_id\":\"do_20044433\",\n" +
                "\"gradeLevel\":[\n" +
                "\"Kindergarten\",\n" +
                "\"Grade 1\",\n" +
                "\"Grade 2\",\n" +
                "\"Grade 3\",\n" +
                "\"Grade 4\",\n" +
                "\"Grade 5\",\n" +
                "\"Other\"\n" +
                "],\n" +
                "\"graph_id\":\"domain\",\n" +
                "\"idealScreenDensity\":\"hdpi\",\n" +
                "\"idealScreenSize\":\"normal\",\n" +
                "\"identifier\":\"do_20044433\",\n" +
                "\"language\":[\n" +
                "\"English\"\n" +
                "],\n" +
                "\"lastPublishedOn\":\"2016-08-09T12:14:48.086+0000\",\n" +
                "\"lastSubmittedOn\":\"2016-08-09T12:12:37.000+0000\",\n" +
                "\"lastUpdatedBy\":\"331\",\n" +
                "\"lastUpdatedOn\":\"2016-08-09T12:14:48.396+0000\",\n" +
                "\"license\":\"Creative Commons Attribution (CC BY)\",\n" +
                "\"mediaType\":\"content\",\n" +
                "\"mimeType\":\"application/vnd.ekstep.ecml-archive\",\n" +
                "\"name\":\"Test_eslpart4\",\n" +
                "\"nodeType\":\"DATA_NODE\",\n" +
                "\"node_id\":44433,\n" +
                "\"objectType\":\"Content\",\n" +
                "\"os\":[\n" +
                "\"All\"\n" +
                "],\n" +
                "\"osId\":\"org.ekstep.quiz.app\",\n" +
                "\"owner\":\"Debasis Singh\",\n" +
                "\"pkgVersion\":2,\n" +
                "\"portalOwner\":\"347\",\n" +
                "\"posterImage\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/1455104397576flowersL.png\",\n" +
                "\"size\":5.896935e+06,\n" +
                "\"status\":\"Live\",\n" +
                "\"visibility\":\"Default\"\n" +
                "},\n" +
                "{\n" +
                "\"ageGroup\":[\n" +
                "\"5-6\"\n" +
                "],\n" +
                "\"appIcon\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/adjective_347_1468922608_1468922650963.thumb.png\",\n" +
                "\"artifactUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/esl_part3_347_1470744664_1470744745906.zip\",\n" +
                "\"code\":\"org.ekstep.literacy.story.1293\",\n" +
                "\"collaborators\":[\n" +
                "\"400\"\n" +
                "],\n" +
                "\"concepts\":[\n" +
                "\"LO52\"\n" +
                "],\n" +
                "\"contentType\":\"Story\",\n" +
                "\"createdOn\":\"2016-07-19T10:02:48.119+0000\",\n" +
                "\"description\":\"test_eslpart3\",\n" +
                "\"domain\":[\n" +
                "\"literacy\"\n" +
                "],\n" +
                "\"downloadUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/test_eslpart3_1470744883217_do_20043455.ecar\",\n" +
                "\"es_metadata_id\":\"do_20043455\",\n" +
                "\"gradeLevel\":[\n" +
                "\"Kindergarten\",\n" +
                "\"Grade 1\",\n" +
                "\"Grade 2\",\n" +
                "\"Grade 3\",\n" +
                "\"Grade 4\",\n" +
                "\"Grade 5\",\n" +
                "\"Other\"\n" +
                "],\n" +
                "\"graph_id\":\"domain\",\n" +
                "\"idealScreenDensity\":\"hdpi\",\n" +
                "\"idealScreenSize\":\"normal\",\n" +
                "\"identifier\":\"do_20043455\",\n" +
                "\"language\":[\n" +
                "\"English\"\n" +
                "],\n" +
                "\"lastPublishedOn\":\"2016-08-09T12:14:46.978+0000\",\n" +
                "\"lastSubmittedOn\":\"2016-08-09T12:12:51.820+0000\",\n" +
                "\"lastUpdatedBy\":\"331\",\n" +
                "\"lastUpdatedOn\":\"2016-08-09T12:14:47.201+0000\",\n" +
                "\"license\":\"Creative Commons Attribution (CC BY)\",\n" +
                "\"mediaType\":\"content\",\n" +
                "\"mimeType\":\"application/vnd.ekstep.ecml-archive\",\n" +
                "\"name\":\"test_eslpart3\",\n" +
                "\"nodeType\":\"DATA_NODE\",\n" +
                "\"node_id\":43455,\n" +
                "\"objectType\":\"Content\",\n" +
                "\"os\":[\n" +
                "\"All\"\n" +
                "],\n" +
                "\"osId\":\"org.ekstep.quiz.app\",\n" +
                "\"owner\":\"Debasis Singh\",\n" +
                "\"pkgVersion\":9,\n" +
                "\"portalOwner\":\"347\",\n" +
                "\"posterImage\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/language_assets/adjective_347_1468922608_1468922650963.png\",\n" +
                "\"size\":2.4765409e+07,\n" +
                "\"status\":\"Live\",\n" +
                "\"visibility\":\"Default\"\n" +
                "}\n" +
                "]\n" +
                "},\n" +
                "{\n" +
                "\"display\":{\n" +
                "\"name\":{\n" +
                "\"en\":\"Top Stories\",\n" +
                "\"hn\":\"à¤²à¥‹à¤•à¤ªà¥à¤°à¤¿à¤¯ à¤•à¤¹à¤¾à¤¨à¤¿à¤¯à¤¾à¤‚\"\n" +
                "}\n" +
                "},\n" +
                "\"search\":{\n" +
                "\"query\":\"\",\n" +
                "\"limit\":10,\n" +
                "\"sort_by\":{\n" +
                "\"popularity\":\"desc\"\n" +
                "},\n" +
                "\"filters\":{\n" +
                "\"contentType\":[\n" +
                "\"Story\"\n" +
                "],\n" +
                "\"objectType\":[\n" +
                "\"Content\"\n" +
                "],\n" +
                "\"status\":[\n" +
                "\"Live\"\n" +
                "]\n" +
                "},\n" +
                "\"facets\":[\n" +
                "\"contentType\",\n" +
                "\"domain\",\n" +
                "\"ageGroup\",\n" +
                "\"language\",\n" +
                "\"gradeLevel\"\n" +
                "]\n" +
                "},\n" +
                "\"recommend\":null,\n" +
                "\"contents\":[\n" +
                "{\n" +
                "\"ageGroup\":[\n" +
                "\"5-6\",\n" +
                "\"6-7\",\n" +
                "\"7-8\",\n" +
                "\"8-10\"\n" +
                "],\n" +
                "\"artifactUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/1467385670957_do_30023208.zip\",\n" +
                "\"code\":\"org.ekstep.literacy.story.629\",\n" +
                "\"collaborators\":[\n" +
                "\"141\"\n" +
                "],\n" +
                "\"collections\":[\n" +
                "\"do_30033076\",\n" +
                "\"do_30022222\"\n" +
                "],\n" +
                "\"concepts\":[\n" +
                "\"LO7\"\n" +
                "],\n" +
                "\"contentType\":\"Story\",\n" +
                "\"copyright\":\"\",\n" +
                "\"createdOn\":\"2016-06-29T05:40:51.924+0000\",\n" +
                "\"description\":\"à¤¯à¤¹à¤¾à¤ à¤ªà¤° à¤†à¤ª 20 à¤›à¥‹à¤Ÿà¥‡ à¤…à¤¨à¥à¤šà¥à¤›à¥‡à¤¦ à¤ªà¤¾à¤à¤à¤—à¥‡ à¤œà¥‹ à¤¸à¥à¤¨à¥‡ à¤”à¤° à¤ªà¥à¥‡ à¤œà¤¾ à¤¸à¤•à¤¤à¥‡ à¤¹à¥ˆà¤‚à¥¤ à¤¹à¤° à¤…à¤¨à¥à¤šà¥à¤›à¥‡à¤¦ à¤•à¥‹ à¤…à¤ªà¤¨à¥€ à¤†à¤µà¤¾à¥› à¤®à¥‡à¤‚ à¤°à¥‡à¤•à¥‰à¤°à¥à¤¡ à¤•à¤°à¤•à¥‡ à¤¸à¥à¤¨à¤¾ à¤œà¤¾ à¤¸à¤•à¤¤à¤¾ à¤¹à¥ˆà¥¤ \",\n" +
                "\"domain\":[\n" +
                "\"literacy\"\n" +
                "],\n" +
                "\"downloadUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/do_30023208_1467385680915.ecar\",\n" +
                "\"es_metadata_id\":\"do_30023208\",\n" +
                "\"gradeLevel\":[\n" +
                "\"Kindergarten\",\n" +
                "\"Grade 1\",\n" +
                "\"Grade 2\",\n" +
                "\"Grade 3\",\n" +
                "\"Grade 4\",\n" +
                "\"Grade 5\",\n" +
                "\"Other\"\n" +
                "],\n" +
                "\"graph_id\":\"domain\",\n" +
                "\"idealScreenDensity\":\"hdpi\",\n" +
                "\"idealScreenSize\":\"normal\",\n" +
                "\"identifier\":\"do_30023208\",\n" +
                "\"language\":[\n" +
                "\"Hindi\"\n" +
                "],\n" +
                "\"lastPublishedOn\":\"2016-07-01T15:08:01.723+0000\",\n" +
                "\"lastSubmittedOn\":\"2016-06-29T05:53:50.150+0000\",\n" +
                "\"lastUpdatedBy\":\"141\",\n" +
                "\"lastUpdatedOn\":\"2016-07-05T09:47:08.236+0000\",\n" +
                "\"license\":\"Creative Commons Attribution (CC BY)\",\n" +
                "\"mediaType\":\"content\",\n" +
                "\"mimeType\":\"application/vnd.ekstep.ecml-archive\",\n" +
                "\"name\":\"PC Paragraph\",\n" +
                "\"nodeType\":\"DATA_NODE\",\n" +
                "\"node_id\":23208,\n" +
                "\"objectType\":\"Content\",\n" +
                "\"optStatus\":\"Complete\",\n" +
                "\"os\":[\n" +
                "\"All\"\n" +
                "],\n" +
                "\"osId\":\"org.ekstep.quiz.app\",\n" +
                "\"owner\":\"Pratham\",\n" +
                "\"pkgVersion\":5,\n" +
                "\"portalOwner\":\"286\",\n" +
                "\"publisher\":\"NA\",\n" +
                "\"size\":7.109925e+06,\n" +
                "\"source\":\"Pratham\",\n" +
                "\"status\":\"Live\",\n" +
                "\"tags\":[\n" +
                "\"decoding\",\n" +
                "\"read\",\n" +
                "\"hindi\",\n" +
                "\"word level\",\n" +
                "\"word\",\n" +
                "\"para\",\n" +
                "\"camal\",\n" +
                "\"para level\",\n" +
                "\"pratham\",\n" +
                "\"pratham camal\"\n" +
                "],\n" +
                "\"visibility\":\"Default\"\n" +
                "},\n" +
                "{\n" +
                "\"ageGroup\":[\n" +
                "\"5-6\",\n" +
                "\"6-7\",\n" +
                "\"7-8\",\n" +
                "\"8-10\"\n" +
                "],\n" +
                "\"appIcon\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/language_assets/Story5_1463740696341.png\",\n" +
                "\"artifactUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/1467611087192_domain_4083.zip\",\n" +
                "\"code\":\"org.ekstep.literacy.story.267\",\n" +
                "\"collaborators\":[\n" +
                "\"206\",\n" +
                "\"141\",\n" +
                "\"224\",\n" +
                "\"286\",\n" +
                "\"350\"\n" +
                "],\n" +
                "\"collections\":[\n" +
                "\"do_30033076\",\n" +
                "\"do_30022222\"\n" +
                "],\n" +
                "\"contentType\":\"Story\",\n" +
                "\"copyright\":\"\",\n" +
                "\"createdOn\":\"2016-05-02T10:03:16.029+0000\",\n" +
                "\"description\":\"à¤à¤• à¤•à¥à¤¤à¥à¤¤à¥‡ à¤•à¥‹ à¤°à¥‹à¤Ÿà¥€ à¤®à¤¿à¤²à¥€à¥¤ à¤ªà¥à¤¿à¤ à¤”à¤° à¤¦à¥‡à¤–à¤¿à¤¯à¥‡ à¤•à¥€ à¤‰à¤¸à¤¨à¥‡ à¤†à¤—à¥‡ à¤•à¥à¤¯à¤¾ à¤•à¤¿à¤¯à¤¾à¥¤ à¤•à¤¹à¤¾à¤¨à¥€ à¤‰à¤¨ à¤¬à¤šà¥à¤šà¥‹à¤‚ à¤•à¥‡ à¤²à¤¿à¤ à¤¹à¥ˆ à¤œà¥‹ à¤…à¤­à¥€ à¤µà¤¾à¤•à¥à¤¯ à¤ªà¥à¤¨à¤¾ à¤¶à¥à¤°à¥‚ à¤•à¤° à¤°à¤¹à¥‡ à¤¹à¥ˆà¤‚à¥¤ à¤•à¤¹à¤¾à¤¨à¥€ à¤•à¥‡ à¤•à¤ à¤¿à¤¨ à¤¶à¤¬à¥à¤¦ à¤•à¤¾ à¤…à¤­à¥à¤¯à¤¾à¤¸ à¤•à¤°à¤¾à¤¯à¤¾ à¤œà¤¾ à¤¸à¤•à¤¤à¤¾ à¤¹à¥ˆà¤‚à¥¤ à¤•à¤¹à¤¾à¤¨à¥€ à¤…à¤ªà¤¨à¥€ à¤†à¤µà¤¾à¥› à¤®à¥‡à¤‚ à¤°à¤¿à¤•à¥‰à¤°à¥à¤¡ à¤•à¤°à¥‡à¤‚ à¤”à¤° à¤¸à¥à¤¨à¥‡à¤‚à¥¤ à¤…à¤‚à¤¤ à¤®à¥‡à¤‚ à¤•à¥à¤› à¤¸à¤µà¤¾à¤² à¤¹à¥ˆà¤‚ à¤œà¥‹ à¤•à¤¹à¤¾à¤¨à¥€ à¤•à¥€ à¤¸à¤®à¤ à¤ªà¤°à¤–à¤¤à¥‡ à¤¹à¥ˆà¤‚à¥¤\",\n" +
                "\"developer\":\"EkStep\",\n" +
                "\"domain\":[\n" +
                "\"literacy\"\n" +
                "],\n" +
                "\"downloadUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/domain_4083_1467611093594.ecar\",\n" +
                "\"es_metadata_id\":\"domain_4083\",\n" +
                "\"genre\":[\n" +
                "\"Fiction\"\n" +
                "],\n" +
                "\"gradeLevel\":[\n" +
                "\"Kindergarten\",\n" +
                "\"Grade 1\",\n" +
                "\"Grade 2\",\n" +
                "\"Grade 3\",\n" +
                "\"Grade 4\",\n" +
                "\"Grade 5\",\n" +
                "\"Other\"\n" +
                "],\n" +
                "\"graph_id\":\"domain\",\n" +
                "\"idealScreenDensity\":\"hdpi\",\n" +
                "\"idealScreenSize\":\"normal\",\n" +
                "\"identifier\":\"domain_4083\",\n" +
                "\"imageCredits\":[\n" +
                "\"ekstep\",\n" +
                "\"323\",\n" +
                "\"Ekstep\",\n" +
                "\"External\"\n" +
                "],\n" +
                "\"language\":[\n" +
                "\"Hindi\"\n" +
                "],\n" +
                "\"lastPublishedOn\":\"2016-07-04T05:44:54.368+0000\",\n" +
                "\"lastSubmittedOn\":\"2016-07-04T04:31:16.477+0000\",\n" +
                "\"lastUpdatedBy\":\"141\",\n" +
                "\"lastUpdatedOn\":\"2016-07-05T09:46:33.972+0000\",\n" +
                "\"license\":\"Creative Commons Attribution (CC BY)\",\n" +
                "\"mediaType\":\"content\",\n" +
                "\"mimeType\":\"application/vnd.ekstep.ecml-archive\",\n" +
                "\"name\":\"à¤•à¥à¤¤à¥à¤¤à¤¾ à¤”à¤° à¤°à¥‹à¤Ÿà¥€\",\n" +
                "\"nodeType\":\"DATA_NODE\",\n" +
                "\"node_id\":4083,\n" +
                "\"objectType\":\"Content\",\n" +
                "\"optStatus\":\"Complete\",\n" +
                "\"os\":[\n" +
                "\"All\"\n" +
                "],\n" +
                "\"osId\":\"org.ekstep.quiz.app\",\n" +
                "\"owner\":\"Pratham\",\n" +
                "\"pkgVersion\":15,\n" +
                "\"portalOwner\":\"EkStep\",\n" +
                "\"publisher\":\"\",\n" +
                "\"size\":6.011039e+06,\n" +
                "\"soundCredits\":[\n" +
                "\"323\",\n" +
                "\"141\",\n" +
                "\"Aditya R\",\n" +
                "\"ekstep\",\n" +
                "\"Ekstep\",\n" +
                "\"EkStep\"\n" +
                "],\n" +
                "\"source\":\"Pratham.\",\n" +
                "\"status\":\"Live\",\n" +
                "\"tags\":[\n" +
                "\"Camp - 1 (Basic)\",\n" +
                "\"Story - 5\",\n" +
                "\"camal story #5\",\n" +
                "\"record and play\",\n" +
                "\"record\",\n" +
                "\"para level\",\n" +
                "\"story level\",\n" +
                "\"story\",\n" +
                "\"para and story level\",\n" +
                "\"kutha and roti\",\n" +
                "\"kutha aur roti\",\n" +
                "\"para\",\n" +
                "\"camal story\",\n" +
                "\"kutta\",\n" +
                "\"kutta aur roti\",\n" +
                "\"kutha\",\n" +
                "\"roti\",\n" +
                "\"hindi\",\n" +
                "\"camal\",\n" +
                "\"hindi stories\",\n" +
                "\"hindi story\",\n" +
                "\"pratham camal\",\n" +
                "\"pratham\",\n" +
                "\"40-60 words\"\n" +
                "],\n" +
                "\"visibility\":\"Default\"\n" +
                "},\n" +
                "{\n" +
                "\"ageGroup\":[\n" +
                "\"5-6\"\n" +
                "],\n" +
                "\"appIcon\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/download_239_1469426013_1469426067255.thumb.jpg\",\n" +
                "\"artifactUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/1469426221645_do_20043780.zip\",\n" +
                "\"code\":\"org.ekstep.literacy.story.1370\",\n" +
                "\"contentType\":\"Story\",\n" +
                "\"createdOn\":\"2016-07-25T05:53:31.086+0000\",\n" +
                "\"description\":\"TEST\",\n" +
                "\"domain\":[\n" +
                "\"literacy\",\n" +
                "\"numeracy\"\n" +
                "],\n" +
                "\"downloadUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/sqs-5522_1469426222102_do_20043780.ecar\",\n" +
                "\"es_metadata_id\":\"do_20043780\",\n" +
                "\"gradeLevel\":[\n" +
                "\"Kindergarten\",\n" +
                "\"Grade 1\",\n" +
                "\"Grade 2\",\n" +
                "\"Grade 3\",\n" +
                "\"Grade 4\",\n" +
                "\"Grade 5\",\n" +
                "\"Other\"\n" +
                "],\n" +
                "\"graph_id\":\"domain\",\n" +
                "\"idealScreenDensity\":\"hdpi\",\n" +
                "\"idealScreenSize\":\"normal\",\n" +
                "\"identifier\":\"do_20043780\",\n" +
                "\"imageCredits\":[\n" +
                "\"ekstep\"\n" +
                "],\n" +
                "\"language\":[\n" +
                "\"Marathi\"\n" +
                "],\n" +
                "\"lastPublishedOn\":\"2016-07-25T05:57:02.490+0000\",\n" +
                "\"lastSubmittedOn\":\"2016-07-25T05:54:27.391+0000\",\n" +
                "\"lastUpdatedBy\":\"382\",\n" +
                "\"lastUpdatedOn\":\"2016-07-25T05:57:02.649+0000\",\n" +
                "\"license\":\"Creative Commons Attribution (CC BY)\",\n" +
                "\"mediaType\":\"content\",\n" +
                "\"mimeType\":\"application/vnd.ekstep.ecml-archive\",\n" +
                "\"name\":\"SQS 5522\",\n" +
                "\"nodeType\":\"DATA_NODE\",\n" +
                "\"node_id\":43780,\n" +
                "\"objectType\":\"Content\",\n" +
                "\"os\":[\n" +
                "\"All\"\n" +
                "],\n" +
                "\"osId\":\"org.ekstep.quiz.app\",\n" +
                "\"owner\":\"External Testing vendor\",\n" +
                "\"pkgVersion\":1,\n" +
                "\"portalOwner\":\"239\",\n" +
                "\"posterImage\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/language_assets/download_239_1469426013_1469426067255.jpg\",\n" +
                "\"size\":1.276963e+06,\n" +
                "\"soundCredits\":[\n" +
                "\"ekstep\"\n" +
                "],\n" +
                "\"status\":\"Live\",\n" +
                "\"visibility\":\"Default\"\n" +
                "},\n" +
                "{\n" +
                "\"ageGroup\":[\n" +
                "\"5-6\"\n" +
                "],\n" +
                "\"artifactUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/verbsii_cdata_1469517241184.zip\",\n" +
                "\"code\":\"Test_QA\",\n" +
                "\"contentType\":\"Story\",\n" +
                "\"createdOn\":\"2016-07-26T06:45:49.129+0000\",\n" +
                "\"description\":\"Test_QA\",\n" +
                "\"downloadUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/test_qa_121_1469517268958_test_qa_6.ecar\",\n" +
                "\"es_metadata_id\":\"Test_QA_6\",\n" +
                "\"gradeLevel\":[\n" +
                "\"Grade 1\"\n" +
                "],\n" +
                "\"graph_id\":\"domain\",\n" +
                "\"idealScreenDensity\":\"hdpi\",\n" +
                "\"idealScreenSize\":\"normal\",\n" +
                "\"identifier\":\"Test_QA_6\",\n" +
                "\"language\":[\n" +
                "\"English\"\n" +
                "],\n" +
                "\"lastPublishedOn\":\"2016-07-26T07:14:31.021+0000\",\n" +
                "\"lastUpdatedOn\":\"2016-07-26T07:14:31.108+0000\",\n" +
                "\"license\":\"Creative Commons Attribution (CC BY)\",\n" +
                "\"mediaType\":\"content\",\n" +
                "\"mimeType\":\"application/vnd.ekstep.ecml-archive\",\n" +
                "\"name\":\"Test_QA_121\",\n" +
                "\"nodeType\":\"DATA_NODE\",\n" +
                "\"node_id\":43819,\n" +
                "\"objectType\":\"Content\",\n" +
                "\"os\":[\n" +
                "\"All\"\n" +
                "],\n" +
                "\"osId\":\"org.ekstep.quiz.app\",\n" +
                "\"pkgVersion\":4,\n" +
                "\"size\":1.4996257e+07,\n" +
                "\"status\":\"Live\",\n" +
                "\"visibility\":\"Default\"\n" +
                "},\n" +
                "{\n" +
                "\"ageGroup\":[\n" +
                "\"5-6\"\n" +
                "],\n" +
                "\"appIcon\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/tick_329_1469708763_1469708822982.thumb.png\",\n" +
                "\"artifactUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/1469708870666_do_20044112.zip\",\n" +
                "\"code\":\"org.ekstep.literacy.story.1419\",\n" +
                "\"contentType\":\"Story\",\n" +
                "\"createdOn\":\"2016-07-28T12:24:26.996+0000\",\n" +
                "\"description\":\"TestingRnP\",\n" +
                "\"domain\":[\n" +
                "\"literacy\"\n" +
                "],\n" +
                "\"downloadUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/testingrnp_1469708872993_do_20044112.ecar\",\n" +
                "\"es_metadata_id\":\"do_20044112\",\n" +
                "\"gradeLevel\":[\n" +
                "\"Kindergarten\",\n" +
                "\"Grade 1\",\n" +
                "\"Grade 2\",\n" +
                "\"Grade 3\",\n" +
                "\"Grade 4\",\n" +
                "\"Grade 5\",\n" +
                "\"Other\"\n" +
                "],\n" +
                "\"graph_id\":\"domain\",\n" +
                "\"idealScreenDensity\":\"hdpi\",\n" +
                "\"idealScreenSize\":\"normal\",\n" +
                "\"identifier\":\"do_20044112\",\n" +
                "\"imageCredits\":[\n" +
                "\"ekstep\"\n" +
                "],\n" +
                "\"language\":[\n" +
                "\"English\"\n" +
                "],\n" +
                "\"lastPublishedOn\":\"2016-07-28T12:27:53.616+0000\",\n" +
                "\"lastSubmittedOn\":\"2016-07-28T12:27:12.557+0000\",\n" +
                "\"lastUpdatedBy\":\"200\",\n" +
                "\"lastUpdatedOn\":\"2016-07-28T12:27:53.757+0000\",\n" +
                "\"license\":\"Creative Commons Attribution (CC BY)\",\n" +
                "\"mediaType\":\"content\",\n" +
                "\"mimeType\":\"application/vnd.ekstep.ecml-archive\",\n" +
                "\"name\":\"TestingRnP\",\n" +
                "\"nodeType\":\"DATA_NODE\",\n" +
                "\"node_id\":44112,\n" +
                "\"objectType\":\"Content\",\n" +
                "\"os\":[\n" +
                "\"All\"\n" +
                "],\n" +
                "\"osId\":\"org.ekstep.quiz.app\",\n" +
                "\"owner\":\"Roy D\",\n" +
                "\"pkgVersion\":2,\n" +
                "\"portalOwner\":\"329\",\n" +
                "\"posterImage\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/language_assets/tick_329_1469708763_1469708822982.png\",\n" +
                "\"size\":3.436985e+06,\n" +
                "\"soundCredits\":[\n" +
                "\"ekstep\"\n" +
                "],\n" +
                "\"status\":\"Live\",\n" +
                "\"visibility\":\"Default\"\n" +
                "},\n" +
                "{\n" +
                "\"ageGroup\":[\n" +
                "\"5-6\"\n" +
                "],\n" +
                "\"appIcon\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/1457097032254potato.thumb.png\",\n" +
                "\"artifactUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/flip_1470137408306.zip\",\n" +
                "\"code\":\"org.ekstep.literacy.story.1469\",\n" +
                "\"concepts\":[\n" +
                "\"LO52\"\n" +
                "],\n" +
                "\"contentType\":\"Story\",\n" +
                "\"createdOn\":\"2016-08-02T07:21:13.169+0000\",\n" +
                "\"description\":\"flip image game in hindi\",\n" +
                "\"domain\":[\n" +
                "\"literacy\"\n" +
                "],\n" +
                "\"downloadUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/flip-image-game_1470137529301_do_20044247.ecar\",\n" +
                "\"es_metadata_id\":\"do_20044247\",\n" +
                "\"gradeLevel\":[\n" +
                "\"Kindergarten\",\n" +
                "\"Grade 1\",\n" +
                "\"Grade 2\",\n" +
                "\"Grade 3\",\n" +
                "\"Grade 4\",\n" +
                "\"Grade 5\",\n" +
                "\"Other\"\n" +
                "],\n" +
                "\"graph_id\":\"domain\",\n" +
                "\"idealScreenDensity\":\"hdpi\",\n" +
                "\"idealScreenSize\":\"normal\",\n" +
                "\"identifier\":\"do_20044247\",\n" +
                "\"language\":[\n" +
                "\"Hindi\"\n" +
                "],\n" +
                "\"lastPublishedOn\":\"2016-08-02T11:32:12.320+0000\",\n" +
                "\"lastSubmittedOn\":\"2016-08-02T07:25:08.220+0000\",\n" +
                "\"lastUpdatedBy\":\"347\",\n" +
                "\"lastUpdatedOn\":\"2016-08-02T11:32:12.415+0000\",\n" +
                "\"license\":\"Creative Commons Attribution (CC BY)\",\n" +
                "\"mediaType\":\"content\",\n" +
                "\"mimeType\":\"application/vnd.ekstep.html-archive\",\n" +
                "\"name\":\"Flip Image Game\",\n" +
                "\"nodeType\":\"DATA_NODE\",\n" +
                "\"node_id\":44247,\n" +
                "\"objectType\":\"Content\",\n" +
                "\"os\":[\n" +
                "\"All\"\n" +
                "],\n" +
                "\"osId\":\"org.ekstep.quiz.app\",\n" +
                "\"owner\":\"Srivathsa Dhanraj\",\n" +
                "\"pkgVersion\":18,\n" +
                "\"portalOwner\":\"200\",\n" +
                "\"posterImage\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/1457097032254potato.png\",\n" +
                "\"size\":2.1834105e+07,\n" +
                "\"status\":\"Live\",\n" +
                "\"visibility\":\"Default\"\n" +
                "},\n" +
                "{\n" +
                "\"ageGroup\":[\n" +
                "\"5-6\"\n" +
                "],\n" +
                "\"artifactUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/build-a-sentence_1470203474272.zip\",\n" +
                "\"code\":\"Test_QA\",\n" +
                "\"contentType\":\"Story\",\n" +
                "\"createdOn\":\"2016-08-03T05:51:13.579+0000\",\n" +
                "\"description\":\"Test_QA\",\n" +
                "\"downloadUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/test_qa_7079046_1470203474994_test_qa_7079046.ecar\",\n" +
                "\"es_metadata_id\":\"Test_QA_7079046\",\n" +
                "\"gradeLevel\":[\n" +
                "\"Grade 1\"\n" +
                "],\n" +
                "\"graph_id\":\"domain\",\n" +
                "\"idealScreenDensity\":\"hdpi\",\n" +
                "\"idealScreenSize\":\"normal\",\n" +
                "\"identifier\":\"Test_QA_7079046\",\n" +
                "\"language\":[\n" +
                "\"English\"\n" +
                "],\n" +
                "\"lastPublishedOn\":\"2016-08-03T05:51:15.337+0000\",\n" +
                "\"lastUpdatedOn\":\"2016-08-03T05:51:15.434+0000\",\n" +
                "\"license\":\"Creative Commons Attribution (CC BY)\",\n" +
                "\"mediaType\":\"content\",\n" +
                "\"mimeType\":\"application/vnd.ekstep.html-archive\",\n" +
                "\"name\":\"Test_QA_7079046\",\n" +
                "\"nodeType\":\"DATA_NODE\",\n" +
                "\"node_id\":44343,\n" +
                "\"objectType\":\"Content\",\n" +
                "\"os\":[\n" +
                "\"All\"\n" +
                "],\n" +
                "\"osId\":\"org.ekstep.quiz.app\",\n" +
                "\"owner\":\"EkStep\",\n" +
                "\"pkgVersion\":4,\n" +
                "\"size\":1.861486e+06,\n" +
                "\"status\":\"Live\",\n" +
                "\"visibility\":\"Default\"\n" +
                "},\n" +
                "{\n" +
                "\"ageGroup\":[\n" +
                "\"5-6\"\n" +
                "],\n" +
                "\"code\":\"Test_QA\",\n" +
                "\"contentType\":\"Story\",\n" +
                "\"createdOn\":\"2016-08-03T09:31:03.639+0000\",\n" +
                "\"description\":\"Test_QA\",\n" +
                "\"es_metadata_id\":\"Test_QA_2502041\",\n" +
                "\"gradeLevel\":[\n" +
                "\"Grade 1\"\n" +
                "],\n" +
                "\"graph_id\":\"domain\",\n" +
                "\"idealScreenDensity\":\"hdpi\",\n" +
                "\"idealScreenSize\":\"normal\",\n" +
                "\"identifier\":\"Test_QA_2502041\",\n" +
                "\"language\":[\n" +
                "\"English\"\n" +
                "],\n" +
                "\"lastUpdatedOn\":\"2016-08-03T09:44:00.030+0000\",\n" +
                "\"license\":\"Creative Commons Attribution (CC BY)\",\n" +
                "\"mediaType\":\"content\",\n" +
                "\"mimeType\":\"application/vnd.ekstep.ecml-archive\",\n" +
                "\"name\":\"Test_QA_2502041\",\n" +
                "\"nodeType\":\"DATA_NODE\",\n" +
                "\"node_id\":44469,\n" +
                "\"objectType\":\"Content\",\n" +
                "\"os\":[\n" +
                "\"All\"\n" +
                "],\n" +
                "\"osId\":\"org.ekstep.quiz.app\",\n" +
                "\"owner\":\"EkStep\",\n" +
                "\"pkgVersion\":3,\n" +
                "\"status\":\"Live\",\n" +
                "\"visibility\":\"Default\"\n" +
                "},\n" +
                "{\n" +
                "\"ageGroup\":[\n" +
                "\"5-6\"\n" +
                "],\n" +
                "\"artifactUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/haircut_story_1470203777004.zip\",\n" +
                "\"code\":\"Test_QA\",\n" +
                "\"contentType\":\"Story\",\n" +
                "\"createdOn\":\"2016-08-03T05:56:09.064+0000\",\n" +
                "\"description\":\"Test_QA\",\n" +
                "\"downloadUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/test_qat-1588_1470203780434_test_qat_1588.ecar\",\n" +
                "\"es_metadata_id\":\"Test_QAT_1588\",\n" +
                "\"gradeLevel\":[\n" +
                "\"Grade 1\"\n" +
                "],\n" +
                "\"graph_id\":\"domain\",\n" +
                "\"idealScreenDensity\":\"hdpi\",\n" +
                "\"idealScreenSize\":\"normal\",\n" +
                "\"identifier\":\"Test_QAT_1588\",\n" +
                "\"language\":[\n" +
                "\"English\"\n" +
                "],\n" +
                "\"lastPublishedOn\":\"2016-08-03T05:56:23.173+0000\",\n" +
                "\"lastUpdatedOn\":\"2016-08-03T05:56:23.238+0000\",\n" +
                "\"license\":\"Creative Commons Attribution (CC BY)\",\n" +
                "\"mediaType\":\"content\",\n" +
                "\"mimeType\":\"application/vnd.ekstep.ecml-archive\",\n" +
                "\"name\":\"Test_QAT-1588\",\n" +
                "\"nodeType\":\"DATA_NODE\",\n" +
                "\"node_id\":44378,\n" +
                "\"objectType\":\"Content\",\n" +
                "\"os\":[\n" +
                "\"All\"\n" +
                "],\n" +
                "\"osId\":\"org.ekstep.quiz.app\",\n" +
                "\"owner\":\"EkStep\",\n" +
                "\"pkgVersion\":4,\n" +
                "\"size\":2.0283664e+07,\n" +
                "\"status\":\"Live\",\n" +
                "\"visibility\":\"Default\"\n" +
                "},\n" +
                "{\n" +
                "\"ageGroup\":[\n" +
                "\"5-6\"\n" +
                "],\n" +
                "\"artifactUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/the_moon_and_the_cap_1470284258956.zip\",\n" +
                "\"code\":\"Test_QA\",\n" +
                "\"contentType\":\"Story\",\n" +
                "\"createdOn\":\"2016-08-04T04:17:31.857+0000\",\n" +
                "\"description\":\"Test_QA\",\n" +
                "\"downloadUrl\":\"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/test_qa_4031770_1470284265394_test_qa_4031770.ecar\",\n" +
                "\"es_metadata_id\":\"Test_QA_4031770\",\n" +
                "\"gradeLevel\":[\n" +
                "\"Grade 1\"\n" +
                "],\n" +
                "\"graph_id\":\"domain\",\n" +
                "\"idealScreenDensity\":\"hdpi\",\n" +
                "\"idealScreenSize\":\"normal\",\n" +
                "\"identifier\":\"Test_QA_4031770\",\n" +
                "\"language\":[\n" +
                "\"English\"\n" +
                "],\n" +
                "\"lastPublishedOn\":\"2016-08-04T04:17:48.878+0000\",\n" +
                "\"lastUpdatedOn\":\"2016-08-04T04:17:48.930+0000\",\n" +
                "\"license\":\"Creative Commons Attribution (CC BY)\",\n" +
                "\"mediaType\":\"content\",\n" +
                "\"mimeType\":\"application/vnd.ekstep.ecml-archive\",\n" +
                "\"name\":\"Test_QA_4031770\",\n" +
                "\"nodeType\":\"DATA_NODE\",\n" +
                "\"node_id\":44526,\n" +
                "\"objectType\":\"Content\",\n" +
                "\"os\":[\n" +
                "\"All\"\n" +
                "],\n" +
                "\"osId\":\"org.ekstep.quiz.app\",\n" +
                "\"owner\":\"EkStep\",\n" +
                "\"pkgVersion\":4,\n" +
                "\"size\":2.7501369e+07,\n" +
                "\"status\":\"Live\",\n" +
                "\"visibility\":\"Default\"\n" +
                "}\n" +
                "]\n" +
                "}\n" +
                "]\n" +
                "}\n" +
                "}\n" +
                "}";
    }

    public static String getLangSearchResponseData() {
        return "{\n" +
                "\"id\":\"langugeSearch\",\n" +
                "\"ver\":\"2.0\",\n" +
                "\"ts\":\"2016-08-30T08:25:37ZZ\",\n" +
                "\"params\":{\n" +
                "\"resmsgid\":\"661d0ba1-3cb2-4d92-a0e4-9040b4355692\",\n" +
                "\"msgid\":null,\n" +
                "\"err\":null,\n" +
                "\"status\":\"successful\",\n" +
                "\"errmsg\":null\n" +
                "},\n" +
                "\"responseCode\":\"OK\",\n" +
                "\"result\":{\n" +
                "\"words\":[\n" +
                "{\n" +
                "\"identifier\":\"en_2\",\n" +
                "\"lemma\":\"cat\",\n" +
                "\"pos\":[\n" +
                "\"noun\"\n" +
                "],\n" +
                "\"pictures\":[\n" +
                "\"https://s3.amazon.com/ekstep-public/language_assets/cat.jpg\"\n" +
                "],\n" +
                "\"pronunciations\":[\n" +
                "\"https://s3.amazon.com/ekstep-public/language_assets/cat.mp3\"\n" +
                "]\n" +
                "}\n" +
                "],\n" +
                "\"relations\":[\n" +
                "{\n" +
                "\"score\":\"6.4\",\n" +
                "\"title\":\"\",\n" +
                "\"list\":[\n" +
                "\"en_2\",\n" +
                "\"en_5\",\n" +
                "\"en_1\"\n" +
                "],\n" +
                "\"relation\":\"akshara_boundary\"\n" +
                "}\n" +
                "]\n" +
                "}\n" +
                "}\n";
    }

    public static String getTraversalRuleResponseData() {
        return "{\n" +
                "\"id\":\"langugeSearch\",\n" +
                "\"ver\":\"2.0\",\n" +
                "\"ts\":\"2016-08-30T08:25:37ZZ\",\n" +
                "\"params\":{\n" +
                "\"resmsgid\":\"661d0ba1-3cb2-4d92-a0e4-9040b4355692\",\n" +
                "\"msgid\":null,\n" +
                "\"err\":null,\n" +
                "\"status\":\"successful\",\n" +
                "\"errmsg\":null\n" +
                "},\n" +
                "\"responseCode\":\"OK\",\n" +
                "\"result\":{\n" +
                "\n" +
                "  \"rules\": [\n" +
                "      {\n" +
                "        \"identifier\": \"rhyming_boundary\",\n" +
                "        \"description\": \"Rhyming rule\",\n" +
                "        \"minChainLength\": 3,\n" +
                "        \"type\": \"RhymingSound\",\n" +
                "        \"createdOn\": \"2016-08-10T17:41:49.883+1000\",\n" +
                "        \"ruleObjectStatus\": [\n" +
                "          \"Live\",\n" +
                "          \"Draft\"\n" +
                "        ],\n" +
                "        \"wordChainWordsSize\": 1000,\n" +
                "        \"ruleObjectType\": \"Word\",\n" +
                "        \"startWordsSize\": 10,\n" +
                "        \"maxChainLength\": 5,\n" +
                "        \"name\": \"Rhyming rule\",\n" +
                "        \"lastUpdatedOn\": \"2016-08-10T17:41:49.883+1000\",\n" +
                "        \"ruleId\": \"rhyming_boundary\",\n" +
                "        \"ruleScript\": \"getRhymingRuleWordChains\",\n" +
                "        \"status\": \"Draft\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"identifier\": \"akshara_boundary\",\n" +
                "        \"description\": \"Akshara rule\",\n" +
                "        \"minChainLength\": 3,\n" +
                "        \"type\": \"AksharaBoundary\",\n" +
                "        \"createdOn\": \"2016-08-10T17:42:13.375+1000\",\n" +
                "        \"ruleObjectStatus\": [\n" +
                "          \"Live\",\n" +
                "          \"Draft\"\n" +
                "        ],\n" +
                "        \"wordChainWordsSize\": 1000,\n" +
                "        \"ruleObjectType\": \"Word\",\n" +
                "        \"startWordsSize\": 10,\n" +
                "        \"maxChainLength\": 5,\n" +
                "        \"name\": \"Akshara rule\",\n" +
                "        \"lastUpdatedOn\": \"2016-08-10T17:42:13.375+1000\",\n" +
                "        \"ruleId\": \"akshara_boundary\",\n" +
                "        \"ruleScript\": \"getAksharaRuleWordChains\",\n" +
                "        \"status\": \"Draft\"\n" +
                "      }\n" +
                "    ]\n" +
                "}\n" +
                "\n" +
                "}\n";
    }

    public static String getPageServiceResponseWith3Banners() {
        return "{\n" +
                "  \"id\": \"ekstep.genie.content.home\",\n" +
                "  \"ver\": \"1.0\",\n" +
                "  \"ts\": \"2016-09-08T11:23:50+05:30\",\n" +
                "  \"params\": {\n" +
                "    \"resmsgid\": \"1809a2c43f26a3874d3f105297769a17263fe528\",\n" +
                "    \"msgid\": \"ff305d54-85b4-341b-da2f-eb6b9e5460fa\",\n" +
                "    \"status\": \"successful\",\n" +
                "    \"err\": \"\",\n" +
                "    \"errmsg\": \"\"\n" +
                "  },\n" +
                "  \"result\": {\n" +
                "    \"page\": {\n" +
                "      \"id\": \"org.ekstep.genie.content.explore\",\n" +
                "      \"banners\": [\n" +
                "        {\n" +
                "          \"alternateText\": \"Stories\",\n" +
                "          \"imageUrl\": \"http://i.imgur.com/fhWNbjd.png\",\n" +
                "          \"url\": \"http://www.ekstep.in/l/all?type=story&sort=popularity\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"alternateText\": \"Stories\",\n" +
                "          \"imageUrl\": \"http://i.imgur.com/fhWNbjd.png\",\n" +
                "          \"url\": \"http://www.ekstep.in/c/org.ekstep.money.worksheet\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"alternateText\": \"Stories\",\n" +
                "          \"imageUrl\": \"http://i.imgur.com/fhWNbjd.png\",\n" +
                "          \"url\": \"http://www.ekstep.in/landing/exploreContent\"\n" +
                "        }\n" +
                "      ],\n" +
                "      \"sections\": [\n" +
                "        {\n" +
                "          \"display\": {\n" +
                "            \"name\": {\n" +
                "              \"en\": \"Most Popular\",\n" +
                "              \"hn\": \"सबसे लोकप्रिय\"\n" +
                "            }\n" +
                "          },\n" +
                "          \"search\": {\n" +
                "            \"query\": \"\",\n" +
                "            \"limit\": 10,\n" +
                "            \"sort_by\": {\n" +
                "              \"popularity\": \"desc\"\n" +
                "            },\n" +
                "            \"filters\": {\n" +
                "              \"contentType\": [\n" +
                "                \"Story\",\n" +
                "                \"Worksheet\",\n" +
                "                \"Collection\"\n" +
                "              ],\n" +
                "              \"objectType\": [\n" +
                "                \"Content\"\n" +
                "              ],\n" +
                "              \"status\": [\n" +
                "                \"Live\"\n" +
                "              ]\n" +
                "            },\n" +
                "            \"facets\": [\n" +
                "              \"contentType\",\n" +
                "              \"domain\",\n" +
                "              \"ageGroup\",\n" +
                "              \"language\",\n" +
                "              \"gradeLevel\"\n" +
                "            ]\n" +
                "          },\n" +
                "          \"recommend\": null,\n" +
                "          \"contents\": [\n" +
                "            {\n" +
                "              \"ageGroup\": [\n" +
                "                \"5-6\",\n" +
                "                \"6-7\",\n" +
                "                \"7-8\"\n" +
                "              ],\n" +
                "              \"appIcon\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/language_assets/146166842111696ed5e8e47-1465900163013_1467801696089.jpg\",\n" +
                "              \"artifactUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/1468411695040_do_30036497.zip\",\n" +
                "              \"code\": \"org.ekstep.literacy.story.815\",\n" +
                "              \"concepts\": [\n" +
                "                \"LO46\"\n" +
                "              ],\n" +
                "              \"contentType\": \"Story\",\n" +
                "              \"copyright\": \"Pratham Books\",\n" +
                "              \"createdOn\": \"2016-07-06T10:31:01.875+0000\",\n" +
                "              \"description\": \"One day Maaloo had to get some potatoes from his kitchen garden. Who do you think helped him? Kaaloo, of course! Read about Maaloo's morning with Kaaloo as they found some 'Aaloo'! Children can read and listen to the story. This story is appropriate for children who starting to read sentences.\",\n" +
                "              \"domain\": [\n" +
                "                \"literacy\"\n" +
                "              ],\n" +
                "              \"downloadUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/do_30036497_1468411698411.ecar\",\n" +
                "              \"es_metadata_id\": \"do_30036497\",\n" +
                "              \"gradeLevel\": [\n" +
                "                \"Kindergarten\",\n" +
                "                \"Grade 1\",\n" +
                "                \"Grade 2\"\n" +
                "              ],\n" +
                "              \"graph_id\": \"domain\",\n" +
                "              \"idealScreenDensity\": \"hdpi\",\n" +
                "              \"idealScreenSize\": \"normal\",\n" +
                "              \"identifier\": \"do_30036497\",\n" +
                "              \"imageCredits\": [\n" +
                "                \"ekstep\",\n" +
                "                \"Parabal Partap Singh\",\n" +
                "                \"Pratham Books\"\n" +
                "              ],\n" +
                "              \"language\": [\n" +
                "                \"English\"\n" +
                "              ],\n" +
                "              \"lastPublishedOn\": \"2016-07-13T12:08:19.467+0000\",\n" +
                "              \"lastSubmittedOn\": \"2016-07-06T14:10:25.638+0000\",\n" +
                "              \"lastUpdatedBy\": \"80\",\n" +
                "              \"lastUpdatedOn\": \"2016-07-13T12:08:20.127+0000\",\n" +
                "              \"license\": \"Creative Commons Attribution (CC BY)\",\n" +
                "              \"mediaType\": \"content\",\n" +
                "              \"mimeType\": \"application/vnd.ekstep.ecml-archive\",\n" +
                "              \"name\": \"Aaloo-Maaloo-Kaaloo\",\n" +
                "              \"nodeType\": \"DATA_NODE\",\n" +
                "              \"node_id\": 36497,\n" +
                "              \"objectType\": \"Content\",\n" +
                "              \"os\": [\n" +
                "                \"All\"\n" +
                "              ],\n" +
                "              \"osId\": \"org.ekstep.quiz.app\",\n" +
                "              \"owner\": \"Vinita Krishna\",\n" +
                "              \"pkgVersion\": 5,\n" +
                "              \"portalOwner\": \"334\",\n" +
                "              \"publisher\": \"Pratham Books\",\n" +
                "              \"size\": 7433551,\n" +
                "              \"soundCredits\": [\n" +
                "                \"ekstep\"\n" +
                "              ],\n" +
                "              \"source\": \"MangoReader\",\n" +
                "              \"status\": \"Live\",\n" +
                "              \"tags\": [\n" +
                "                \"prathamdigitalcamal\",\n" +
                "                \"friends\",\n" +
                "                \"pratham books\",\n" +
                "                \"courage\",\n" +
                "                \"nature\",\n" +
                "                \"fun\",\n" +
                "                \"mangosense\"\n" +
                "              ],\n" +
                "              \"visibility\": \"Default\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"ageGroup\": [\n" +
                "                \"5-6\"\n" +
                "              ],\n" +
                "              \"appIcon\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/adjective_347_1468566136_1468566172028.thumb.png\",\n" +
                "              \"artifactUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/pratham1_347_1470910567_1470910651557.zip\",\n" +
                "              \"code\": \"org.ekstep.literacy.story.1211\",\n" +
                "              \"concepts\": [\n" +
                "                \"LO52\"\n" +
                "              ],\n" +
                "              \"contentType\": \"Story\",\n" +
                "              \"copyright\": \"\",\n" +
                "              \"createdOn\": \"2016-07-15T06:56:57.362+0000\",\n" +
                "              \"description\": \"test_prathamstoriesfirstday\",\n" +
                "              \"domain\": [\n" +
                "                \"literacy\"\n" +
                "              ],\n" +
                "              \"downloadUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/test_prathamstoriesfirstday_1470910766701_do_20043357.ecar\",\n" +
                "              \"es_metadata_id\": \"do_20043357\",\n" +
                "              \"gradeLevel\": [\n" +
                "                \"Kindergarten\",\n" +
                "                \"Grade 1\",\n" +
                "                \"Grade 2\",\n" +
                "                \"Grade 3\",\n" +
                "                \"Grade 4\",\n" +
                "                \"Grade 5\",\n" +
                "                \"Other\"\n" +
                "              ],\n" +
                "              \"graph_id\": \"domain\",\n" +
                "              \"idealScreenDensity\": \"hdpi\",\n" +
                "              \"idealScreenSize\": \"normal\",\n" +
                "              \"identifier\": \"do_20043357\",\n" +
                "              \"language\": [\n" +
                "                \"English\"\n" +
                "              ],\n" +
                "              \"lastPublishedOn\": \"2016-08-11T10:19:29.789+0000\",\n" +
                "              \"lastSubmittedOn\": \"2016-08-11T10:18:02.189+0000\",\n" +
                "              \"lastUpdatedBy\": \"331\",\n" +
                "              \"lastUpdatedOn\": \"2016-08-11T10:19:29.964+0000\",\n" +
                "              \"license\": \"Creative Commons Attribution (CC BY)\",\n" +
                "              \"mediaType\": \"content\",\n" +
                "              \"mimeType\": \"application/vnd.ekstep.ecml-archive\",\n" +
                "              \"name\": \"test_prathamstoriesfirstday\",\n" +
                "              \"nodeType\": \"DATA_NODE\",\n" +
                "              \"node_id\": 43357,\n" +
                "              \"objectType\": \"Content\",\n" +
                "              \"os\": [\n" +
                "                \"All\"\n" +
                "              ],\n" +
                "              \"osId\": \"org.ekstep.quiz.app\",\n" +
                "              \"owner\": \"Debasis Singh\",\n" +
                "              \"pkgVersion\": 6,\n" +
                "              \"portalOwner\": \"347\",\n" +
                "              \"posterImage\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/language_assets/adjective_347_1468566136_1468566172028.png\",\n" +
                "              \"publisher\": \"\",\n" +
                "              \"size\": 11906723,\n" +
                "              \"source\": \"\",\n" +
                "              \"status\": \"Live\",\n" +
                "              \"visibility\": \"Default\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"ageGroup\": [\n" +
                "                \"5-6\"\n" +
                "              ],\n" +
                "              \"artifactUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/externaljsonitemdatacdata_1470284037512.zip\",\n" +
                "              \"code\": \"Test_QA\",\n" +
                "              \"contentType\": \"Story\",\n" +
                "              \"createdOn\": \"2016-08-04T04:13:48.806+0000\",\n" +
                "              \"description\": \"Test_QA\",\n" +
                "              \"downloadUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/test_qa_2191396_1470284042095_test_qa_2191396.ecar\",\n" +
                "              \"es_metadata_id\": \"Test_QA_2191396\",\n" +
                "              \"gradeLevel\": [\n" +
                "                \"Grade 1\"\n" +
                "              ],\n" +
                "              \"graph_id\": \"domain\",\n" +
                "              \"idealScreenDensity\": \"hdpi\",\n" +
                "              \"idealScreenSize\": \"normal\",\n" +
                "              \"identifier\": \"Test_QA_2191396\",\n" +
                "              \"language\": [\n" +
                "                \"English\"\n" +
                "              ],\n" +
                "              \"lastPublishedOn\": \"2016-08-04T04:14:05.583+0000\",\n" +
                "              \"lastUpdatedOn\": \"2016-08-04T04:14:05.639+0000\",\n" +
                "              \"license\": \"Creative Commons Attribution (CC BY)\",\n" +
                "              \"mediaType\": \"content\",\n" +
                "              \"mimeType\": \"application/vnd.ekstep.ecml-archive\",\n" +
                "              \"name\": \"Test_QA_2191396\",\n" +
                "              \"nodeType\": \"DATA_NODE\",\n" +
                "              \"node_id\": 44520,\n" +
                "              \"objectType\": \"Content\",\n" +
                "              \"os\": [\n" +
                "                \"All\"\n" +
                "              ],\n" +
                "              \"osId\": \"org.ekstep.quiz.app\",\n" +
                "              \"owner\": \"EkStep\",\n" +
                "              \"pkgVersion\": 4,\n" +
                "              \"size\": 27488612,\n" +
                "              \"status\": \"Live\",\n" +
                "              \"visibility\": \"Default\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"ageGroup\": [\n" +
                "                \"5-6\"\n" +
                "              ],\n" +
                "              \"artifactUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/the_moon_and_the_cap_1470290129650.zip\",\n" +
                "              \"code\": \"Test_QA\",\n" +
                "              \"collections\": [\n" +
                "                \"Test_QA_Collection2983923\"\n" +
                "              ],\n" +
                "              \"contentType\": \"Story\",\n" +
                "              \"createdOn\": \"2016-08-04T05:55:18.656+0000\",\n" +
                "              \"description\": \"Test_QA\",\n" +
                "              \"downloadUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/test_qat-646_1470290135262_test_qat_646.ecar\",\n" +
                "              \"es_metadata_id\": \"Test_QAT_646\",\n" +
                "              \"gradeLevel\": [\n" +
                "                \"Grade 1\"\n" +
                "              ],\n" +
                "              \"graph_id\": \"domain\",\n" +
                "              \"idealScreenDensity\": \"hdpi\",\n" +
                "              \"idealScreenSize\": \"normal\",\n" +
                "              \"identifier\": \"Test_QAT_646\",\n" +
                "              \"language\": [\n" +
                "                \"English\"\n" +
                "              ],\n" +
                "              \"lastPublishedOn\": \"2016-08-04T05:55:38.843+0000\",\n" +
                "              \"lastUpdatedOn\": \"2016-08-04T05:55:38.910+0000\",\n" +
                "              \"license\": \"Creative Commons Attribution (CC BY)\",\n" +
                "              \"mediaType\": \"content\",\n" +
                "              \"mimeType\": \"application/vnd.ekstep.ecml-archive\",\n" +
                "              \"name\": \"Test_QAT-646\",\n" +
                "              \"nodeType\": \"DATA_NODE\",\n" +
                "              \"node_id\": 44550,\n" +
                "              \"objectType\": \"Content\",\n" +
                "              \"os\": [\n" +
                "                \"All\"\n" +
                "              ],\n" +
                "              \"osId\": \"org.ekstep.quiz.app\",\n" +
                "              \"owner\": \"EkStep\",\n" +
                "              \"pkgVersion\": 4,\n" +
                "              \"size\": 27500899,\n" +
                "              \"status\": \"Live\",\n" +
                "              \"visibility\": \"Default\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"ageGroup\": [\n" +
                "                \"5-6\"\n" +
                "              ],\n" +
                "              \"artifactUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/ecml_without_asset_1470294356676.zip\",\n" +
                "              \"code\": \"Test_QA\",\n" +
                "              \"contentType\": \"Story\",\n" +
                "              \"createdOn\": \"2016-08-04T07:05:56.107+0000\",\n" +
                "              \"description\": \"Test_QA\",\n" +
                "              \"downloadUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/test_qa_4183482_1470294359428_test_qa_4183482.ecar\",\n" +
                "              \"es_metadata_id\": \"Test_QA_4183482\",\n" +
                "              \"gradeLevel\": [\n" +
                "                \"Grade 1\"\n" +
                "              ],\n" +
                "              \"graph_id\": \"domain\",\n" +
                "              \"idealScreenDensity\": \"hdpi\",\n" +
                "              \"idealScreenSize\": \"normal\",\n" +
                "              \"identifier\": \"Test_QA_4183482\",\n" +
                "              \"language\": [\n" +
                "                \"English\"\n" +
                "              ],\n" +
                "              \"lastPublishedOn\": \"2016-08-04T07:05:59.545+0000\",\n" +
                "              \"lastUpdatedOn\": \"2016-08-04T07:05:59.615+0000\",\n" +
                "              \"license\": \"Creative Commons Attribution (CC BY)\",\n" +
                "              \"mediaType\": \"content\",\n" +
                "              \"mimeType\": \"application/vnd.ekstep.ecml-archive\",\n" +
                "              \"name\": \"Test_QA_4183482\",\n" +
                "              \"nodeType\": \"DATA_NODE\",\n" +
                "              \"node_id\": 44613,\n" +
                "              \"objectType\": \"Content\",\n" +
                "              \"os\": [\n" +
                "                \"All\"\n" +
                "              ],\n" +
                "              \"osId\": \"org.ekstep.quiz.app\",\n" +
                "              \"owner\": \"EkStep\",\n" +
                "              \"pkgVersion\": 4,\n" +
                "              \"size\": 2938,\n" +
                "              \"status\": \"Live\",\n" +
                "              \"visibility\": \"Default\"\n" +
                "            }\n" +
                "          ]\n" +
                "        },\n" +
                "        {\n" +
                "          \"display\": {\n" +
                "            \"name\": {\n" +
                "              \"en\": \"Newest\",\n" +
                "              \"hn\": \"सबसे नए\"\n" +
                "            }\n" +
                "          },\n" +
                "          \"search\": {\n" +
                "            \"query\": \"\",\n" +
                "            \"limit\": 10,\n" +
                "            \"sort_by\": {\n" +
                "              \"lastUpdatedOn\": \"desc\"\n" +
                "            },\n" +
                "            \"filters\": {\n" +
                "              \"contentType\": [\n" +
                "                \"Story\",\n" +
                "                \"Worksheet\",\n" +
                "                \"Collection\"\n" +
                "              ],\n" +
                "              \"objectType\": [\n" +
                "                \"Content\"\n" +
                "              ],\n" +
                "              \"status\": [\n" +
                "                \"Live\"\n" +
                "              ]\n" +
                "            },\n" +
                "            \"facets\": [\n" +
                "              \"contentType\",\n" +
                "              \"domain\",\n" +
                "              \"ageGroup\",\n" +
                "              \"language\",\n" +
                "              \"gradeLevel\"\n" +
                "            ]\n" +
                "          },\n" +
                "          \"recommend\": null,\n" +
                "          \"contents\": [\n" +
                "            {\n" +
                "              \"ageGroup\": [\n" +
                "                \"5-6\"\n" +
                "              ],\n" +
                "              \"appIcon\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/download_436_1472634061_1472634182056.thumb.jpg\",\n" +
                "              \"artifactUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/flip_1470137408306_436_1473309322_1473309457214.zip\",\n" +
                "              \"code\": \"org.ekstep.literacy.story.2073\",\n" +
                "              \"contentType\": \"Story\",\n" +
                "              \"createdOn\": \"2016-09-08T04:36:22.474+0000\",\n" +
                "              \"description\": \"test\",\n" +
                "              \"domain\": [\n" +
                "                \"literacy\"\n" +
                "              ],\n" +
                "              \"downloadUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/test-html_1473309550402_do_20046395.ecar\",\n" +
                "              \"es_metadata_id\": \"do_20046395\",\n" +
                "              \"gradeLevel\": [\n" +
                "                \"Kindergarten\",\n" +
                "                \"Grade 1\",\n" +
                "                \"Grade 2\",\n" +
                "                \"Grade 3\",\n" +
                "                \"Grade 4\",\n" +
                "                \"Grade 5\",\n" +
                "                \"Other\"\n" +
                "              ],\n" +
                "              \"graph_id\": \"domain\",\n" +
                "              \"idealScreenDensity\": \"hdpi\",\n" +
                "              \"idealScreenSize\": \"normal\",\n" +
                "              \"identifier\": \"do_20046395\",\n" +
                "              \"language\": [\n" +
                "                \"Kannada\"\n" +
                "              ],\n" +
                "              \"lastPublishedOn\": \"2016-09-08T04:39:13.323+0000\",\n" +
                "              \"lastSubmittedOn\": \"2016-09-08T04:37:52.046+0000\",\n" +
                "              \"lastUpdatedBy\": \"239\",\n" +
                "              \"lastUpdatedOn\": \"2016-09-08T04:39:13.392+0000\",\n" +
                "              \"license\": \"Creative Commons Attribution (CC BY)\",\n" +
                "              \"mediaType\": \"content\",\n" +
                "              \"mimeType\": \"application/vnd.ekstep.html-archive\",\n" +
                "              \"name\": \"test html\",\n" +
                "              \"nodeType\": \"DATA_NODE\",\n" +
                "              \"node_id\": 46395,\n" +
                "              \"objectType\": \"Content\",\n" +
                "              \"os\": [\n" +
                "                \"All\"\n" +
                "              ],\n" +
                "              \"osId\": \"org.ekstep.quiz.app\",\n" +
                "              \"owner\": \"ekstep2 sqs\",\n" +
                "              \"pkgVersion\": 1,\n" +
                "              \"portalOwner\": \"436\",\n" +
                "              \"posterImage\": \"https://qa.ekstep.in/assets/public/content/download_436_1472634061_1472634182056.jpg\",\n" +
                "              \"size\": 21796967,\n" +
                "              \"status\": \"Live\",\n" +
                "              \"visibility\": \"Default\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"ageGroup\": [\n" +
                "                \"5-6\"\n" +
                "              ],\n" +
                "              \"appIcon\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/download-1-_273_1468491875_1468491909358.thumb.jpg\",\n" +
                "              \"artifactUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/1468492084671_do_20043321.zip\",\n" +
                "              \"code\": \"org.ekstep.numeracy.story.1186\",\n" +
                "              \"collaborators\": [\n" +
                "                \"324\"\n" +
                "              ],\n" +
                "              \"collections\": [\n" +
                "                \"do_20043220\",\n" +
                "                \"do_20046297\"\n" +
                "              ],\n" +
                "              \"concepts\": [\n" +
                "                \"Num:C3:SC1:MC22\"\n" +
                "              ],\n" +
                "              \"contentType\": \"Story\",\n" +
                "              \"copyright\": \"\",\n" +
                "              \"createdOn\": \"2016-07-14T10:20:10.625+0000\",\n" +
                "              \"description\": \"Need for speed - Most wanted\",\n" +
                "              \"domain\": [\n" +
                "                \"numeracy\"\n" +
                "              ],\n" +
                "              \"downloadUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/need-for-speed-most-wanted_1468492085099_do_20043321.ecar\",\n" +
                "              \"es_metadata_id\": \"do_20043321\",\n" +
                "              \"gradeLevel\": [\n" +
                "                \"Kindergarten\",\n" +
                "                \"Grade 1\",\n" +
                "                \"Grade 2\",\n" +
                "                \"Grade 3\",\n" +
                "                \"Grade 4\",\n" +
                "                \"Grade 5\",\n" +
                "                \"Other\"\n" +
                "              ],\n" +
                "              \"graph_id\": \"domain\",\n" +
                "              \"idealScreenDensity\": \"hdpi\",\n" +
                "              \"idealScreenSize\": \"normal\",\n" +
                "              \"identifier\": \"do_20043321\",\n" +
                "              \"imageCredits\": [\n" +
                "                \"EkStep\",\n" +
                "                \"ekstep\"\n" +
                "              ],\n" +
                "              \"language\": [\n" +
                "                \"English\"\n" +
                "              ],\n" +
                "              \"lastPublishedOn\": \"2016-07-14T10:28:05.436+0000\",\n" +
                "              \"lastSubmittedOn\": \"2016-07-14T10:25:09.487+0000\",\n" +
                "              \"lastUpdatedBy\": \"273\",\n" +
                "              \"lastUpdatedOn\": \"2016-09-02T10:14:55.098+0000\",\n" +
                "              \"license\": \"Creative Commons Attribution (CC BY)\",\n" +
                "              \"mediaType\": \"content\",\n" +
                "              \"mimeType\": \"application/vnd.ekstep.ecml-archive\",\n" +
                "              \"name\": \"Need for speed - Most wanted\",\n" +
                "              \"nodeType\": \"DATA_NODE\",\n" +
                "              \"node_id\": 43321,\n" +
                "              \"objectType\": \"Content\",\n" +
                "              \"os\": [\n" +
                "                \"All\"\n" +
                "              ],\n" +
                "              \"osId\": \"org.ekstep.quiz.app\",\n" +
                "              \"owner\": \"Umesh Thorat Tekdi\",\n" +
                "              \"pkgVersion\": 1,\n" +
                "              \"portalOwner\": \"273\",\n" +
                "              \"posterImage\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/language_assets/download-1-_273_1468491875_1468491909358.jpg\",\n" +
                "              \"publisher\": \"\",\n" +
                "              \"size\": 1348584,\n" +
                "              \"soundCredits\": [\n" +
                "                \"ekstep\"\n" +
                "              ],\n" +
                "              \"source\": \"\",\n" +
                "              \"status\": \"Live\",\n" +
                "              \"visibility\": \"Default\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"ageGroup\": [\n" +
                "                \"5-6\"\n" +
                "              ],\n" +
                "              \"appIcon\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/download_436_1472634061_1472634182056.thumb.jpg\",\n" +
                "              \"artifactUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/1472805463885_do_20046116.zip\",\n" +
                "              \"code\": \"org.ekstep.literacy.story.1999\",\n" +
                "              \"concepts\": [\n" +
                "                \"LO51\"\n" +
                "              ],\n" +
                "              \"contentType\": \"Story\",\n" +
                "              \"copyright\": \"\",\n" +
                "              \"createdOn\": \"2016-09-01T05:22:21.266+0000\",\n" +
                "              \"description\": \"test\",\n" +
                "              \"domain\": [\n" +
                "                \"literacy\"\n" +
                "              ],\n" +
                "              \"downloadUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/test-004_1472805464718_do_20046116.ecar\",\n" +
                "              \"es_metadata_id\": \"do_20046116\",\n" +
                "              \"gradeLevel\": [\n" +
                "                \"Kindergarten\",\n" +
                "                \"Grade 1\",\n" +
                "                \"Grade 2\",\n" +
                "                \"Grade 3\",\n" +
                "                \"Grade 4\",\n" +
                "                \"Grade 5\",\n" +
                "                \"Other\"\n" +
                "              ],\n" +
                "              \"graph_id\": \"domain\",\n" +
                "              \"idealScreenDensity\": \"hdpi\",\n" +
                "              \"idealScreenSize\": \"normal\",\n" +
                "              \"identifier\": \"do_20046116\",\n" +
                "              \"imageCredits\": [\n" +
                "                \"ekstep\"\n" +
                "              ],\n" +
                "              \"language\": [\n" +
                "                \"Marathi\"\n" +
                "              ],\n" +
                "              \"lastPublishedOn\": \"2016-09-02T08:37:45.119+0000\",\n" +
                "              \"lastSubmittedOn\": \"2016-09-01T05:22:56.437+0000\",\n" +
                "              \"lastUpdatedBy\": \"239\",\n" +
                "              \"lastUpdatedOn\": \"2016-09-02T08:37:45.282+0000\",\n" +
                "              \"license\": \"Creative Commons Attribution (CC BY)\",\n" +
                "              \"mediaType\": \"content\",\n" +
                "              \"mimeType\": \"application/vnd.ekstep.ecml-archive\",\n" +
                "              \"name\": \"Test 004\",\n" +
                "              \"nodeType\": \"DATA_NODE\",\n" +
                "              \"node_id\": 46116,\n" +
                "              \"objectType\": \"Content\",\n" +
                "              \"os\": [\n" +
                "                \"All\"\n" +
                "              ],\n" +
                "              \"osId\": \"org.ekstep.quiz.app\",\n" +
                "              \"owner\": \"ekstep2 sqs\",\n" +
                "              \"pkgVersion\": 1,\n" +
                "              \"portalOwner\": \"436\",\n" +
                "              \"posterImage\": \"https://qa.ekstep.in/assets/public/content/download_436_1472634061_1472634182056.jpg\",\n" +
                "              \"publisher\": \"\",\n" +
                "              \"size\": 1243609,\n" +
                "              \"soundCredits\": [\n" +
                "                \"ekstep\"\n" +
                "              ],\n" +
                "              \"source\": \"\",\n" +
                "              \"status\": \"Live\",\n" +
                "              \"visibility\": \"Default\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"ageGroup\": [\n" +
                "                \"5-6\"\n" +
                "              ],\n" +
                "              \"appIcon\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/adjective_347_1468228322_1468228350768.thumb.png\",\n" +
                "              \"artifactUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/clocks_347_1472637459_1472637579728.zip\",\n" +
                "              \"code\": \"org.ekstep.literacy.story.1054\",\n" +
                "              \"collaborators\": [\n" +
                "                \"400\"\n" +
                "              ],\n" +
                "              \"concepts\": [\n" +
                "                \"LO52\"\n" +
                "              ],\n" +
                "              \"contentType\": \"Story\",\n" +
                "              \"copyright\": \"\",\n" +
                "              \"createdOn\": \"2016-07-11T09:11:07.910+0000\",\n" +
                "              \"description\": \"test_clocks\",\n" +
                "              \"domain\": [\n" +
                "                \"literacy\"\n" +
                "              ],\n" +
                "              \"downloadUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/test_clocks_1472721276474_do_20043158.ecar\",\n" +
                "              \"es_metadata_id\": \"do_20043158\",\n" +
                "              \"gradeLevel\": [\n" +
                "                \"Kindergarten\",\n" +
                "                \"Grade 1\",\n" +
                "                \"Grade 2\",\n" +
                "                \"Grade 3\",\n" +
                "                \"Grade 4\",\n" +
                "                \"Grade 5\",\n" +
                "                \"Other\"\n" +
                "              ],\n" +
                "              \"graph_id\": \"domain\",\n" +
                "              \"idealScreenDensity\": \"hdpi\",\n" +
                "              \"idealScreenSize\": \"normal\",\n" +
                "              \"identifier\": \"do_20043158\",\n" +
                "              \"language\": [\n" +
                "                \"English\"\n" +
                "              ],\n" +
                "              \"lastPublishedOn\": \"2016-09-01T09:14:37.405+0000\",\n" +
                "              \"lastSubmittedOn\": \"2016-08-31T09:59:53.564+0000\",\n" +
                "              \"lastUpdatedBy\": \"331\",\n" +
                "              \"lastUpdatedOn\": \"2016-09-01T09:14:37.598+0000\",\n" +
                "              \"license\": \"Creative Commons Attribution (CC BY)\",\n" +
                "              \"mediaType\": \"content\",\n" +
                "              \"mimeType\": \"application/vnd.ekstep.ecml-archive\",\n" +
                "              \"name\": \"test_clocks\",\n" +
                "              \"nodeType\": \"DATA_NODE\",\n" +
                "              \"node_id\": 43158,\n" +
                "              \"objectType\": \"Content\",\n" +
                "              \"os\": [\n" +
                "                \"All\"\n" +
                "              ],\n" +
                "              \"osId\": \"org.ekstep.quiz.app\",\n" +
                "              \"owner\": \"Debasis Singh\",\n" +
                "              \"pkgVersion\": 8,\n" +
                "              \"portalOwner\": \"347\",\n" +
                "              \"posterImage\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/language_assets/adjective_347_1468228322_1468228350768.png\",\n" +
                "              \"publisher\": \"\",\n" +
                "              \"size\": 4831773,\n" +
                "              \"source\": \"\",\n" +
                "              \"status\": \"Live\",\n" +
                "              \"visibility\": \"Default\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"ageGroup\": [\n" +
                "                \"5-6\"\n" +
                "              ],\n" +
                "              \"artifactUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/ecml_with_json_1472644595586.zip\",\n" +
                "              \"code\": \"Test_QA\",\n" +
                "              \"contentType\": \"Story\",\n" +
                "              \"createdOn\": \"2016-08-31T11:56:33.732+0000\",\n" +
                "              \"description\": \"Test_QA\",\n" +
                "              \"downloadUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/lp_ft_2036_1472644597060_lp_ft_2036.ecar\",\n" +
                "              \"es_metadata_id\": \"LP_FT_2036\",\n" +
                "              \"gradeLevel\": [\n" +
                "                \"Grade 1\"\n" +
                "              ],\n" +
                "              \"graph_id\": \"domain\",\n" +
                "              \"idealScreenDensity\": \"hdpi\",\n" +
                "              \"idealScreenSize\": \"normal\",\n" +
                "              \"identifier\": \"LP_FT_2036\",\n" +
                "              \"language\": [\n" +
                "                \"English\"\n" +
                "              ],\n" +
                "              \"lastPublishedOn\": \"2016-08-31T11:56:37.384+0000\",\n" +
                "              \"lastUpdatedOn\": \"2016-08-31T11:56:37.440+0000\",\n" +
                "              \"license\": \"Creative Commons Attribution (CC BY)\",\n" +
                "              \"mediaType\": \"content\",\n" +
                "              \"mimeType\": \"application/vnd.ekstep.ecml-archive\",\n" +
                "              \"name\": \"LP_FT_2036\",\n" +
                "              \"nodeType\": \"DATA_NODE\",\n" +
                "              \"node_id\": 46089,\n" +
                "              \"objectType\": \"Content\",\n" +
                "              \"os\": [\n" +
                "                \"All\"\n" +
                "              ],\n" +
                "              \"osId\": \"org.ekstep.quiz.app\",\n" +
                "              \"owner\": \"EkStep\",\n" +
                "              \"pkgVersion\": 4,\n" +
                "              \"size\": 1227686,\n" +
                "              \"status\": \"Live\",\n" +
                "              \"tags\": [\n" +
                "                \"LP_functionalTest\"\n" +
                "              ],\n" +
                "              \"visibility\": \"Default\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"ageGroup\": [\n" +
                "                \"5-6\"\n" +
                "              ],\n" +
                "              \"appIcon\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/short_stories_aholeinthefence2_1468489607319.thumb.jpg\",\n" +
                "              \"artifactUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/1472551163263_do_20045289.zip\",\n" +
                "              \"code\": \"org.ekstep.literacy.story.1737\",\n" +
                "              \"concepts\": [\n" +
                "                \"LO52\"\n" +
                "              ],\n" +
                "              \"contentType\": \"Story\",\n" +
                "              \"copyright\": \"\",\n" +
                "              \"createdOn\": \"2016-08-11T05:40:56.070+0000\",\n" +
                "              \"description\": \"test\",\n" +
                "              \"domain\": [\n" +
                "                \"literacy\"\n" +
                "              ],\n" +
                "              \"downloadUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/aaaa-test_1472551164493_do_20045289.ecar\",\n" +
                "              \"es_metadata_id\": \"do_20045289\",\n" +
                "              \"gradeLevel\": [\n" +
                "                \"Kindergarten\",\n" +
                "                \"Grade 1\",\n" +
                "                \"Grade 2\",\n" +
                "                \"Grade 3\",\n" +
                "                \"Grade 4\",\n" +
                "                \"Grade 5\",\n" +
                "                \"Other\"\n" +
                "              ],\n" +
                "              \"graph_id\": \"domain\",\n" +
                "              \"idealScreenDensity\": \"hdpi\",\n" +
                "              \"idealScreenSize\": \"normal\",\n" +
                "              \"identifier\": \"do_20045289\",\n" +
                "              \"imageCredits\": [\n" +
                "                \"External Testing vendor\",\n" +
                "                \"ekstep\"\n" +
                "              ],\n" +
                "              \"language\": [\n" +
                "                \"Tamil\"\n" +
                "              ],\n" +
                "              \"lastPublishedOn\": \"2016-08-30T09:59:24.815+0000\",\n" +
                "              \"lastSubmittedOn\": \"2016-08-11T05:50:59.251+0000\",\n" +
                "              \"lastUpdatedBy\": \"80\",\n" +
                "              \"lastUpdatedOn\": \"2016-08-30T09:59:24.963+0000\",\n" +
                "              \"license\": \"Creative Commons Attribution (CC BY)\",\n" +
                "              \"mediaType\": \"content\",\n" +
                "              \"mimeType\": \"application/vnd.ekstep.ecml-archive\",\n" +
                "              \"name\": \"AAAA test\",\n" +
                "              \"nodeType\": \"DATA_NODE\",\n" +
                "              \"node_id\": 45289,\n" +
                "              \"objectType\": \"Content\",\n" +
                "              \"os\": [\n" +
                "                \"All\"\n" +
                "              ],\n" +
                "              \"osId\": \"org.ekstep.quiz.app\",\n" +
                "              \"owner\": \"External Testing vendor\",\n" +
                "              \"pkgVersion\": 2,\n" +
                "              \"portalOwner\": \"239\",\n" +
                "              \"posterImage\": \"https://qa.ekstep.in/assets/public/content/short_stories_aholeinthefence2_1468489607319.jpg\",\n" +
                "              \"publisher\": \"\",\n" +
                "              \"size\": 1244810,\n" +
                "              \"soundCredits\": [\n" +
                "                \"ekstep\"\n" +
                "              ],\n" +
                "              \"source\": \"\",\n" +
                "              \"status\": \"Live\",\n" +
                "              \"visibility\": \"Default\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"ageGroup\": [\n" +
                "                \"5-6\"\n" +
                "              ],\n" +
                "              \"artifactUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/haircut_story_1472540998268.zip\",\n" +
                "              \"code\": \"Test_QA\",\n" +
                "              \"contentType\": \"Story\",\n" +
                "              \"createdOn\": \"2016-08-30T07:09:53.201+0000\",\n" +
                "              \"description\": \"Test_QA\",\n" +
                "              \"downloadUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/lp_ft_t-901_1472541002991_lp_ft_t_901.ecar\",\n" +
                "              \"es_metadata_id\": \"LP_FT_T_901\",\n" +
                "              \"gradeLevel\": [\n" +
                "                \"Grade 1\"\n" +
                "              ],\n" +
                "              \"graph_id\": \"domain\",\n" +
                "              \"idealScreenDensity\": \"hdpi\",\n" +
                "              \"idealScreenSize\": \"normal\",\n" +
                "              \"identifier\": \"LP_FT_T_901\",\n" +
                "              \"language\": [\n" +
                "                \"English\"\n" +
                "              ],\n" +
                "              \"lastPublishedOn\": \"2016-08-30T07:10:05.811+0000\",\n" +
                "              \"lastUpdatedOn\": \"2016-08-30T07:10:05.879+0000\",\n" +
                "              \"license\": \"Creative Commons Attribution (CC BY)\",\n" +
                "              \"mediaType\": \"content\",\n" +
                "              \"mimeType\": \"application/vnd.ekstep.ecml-archive\",\n" +
                "              \"name\": \"LP_FT_T-901\",\n" +
                "              \"nodeType\": \"DATA_NODE\",\n" +
                "              \"node_id\": 45993,\n" +
                "              \"objectType\": \"Content\",\n" +
                "              \"os\": [\n" +
                "                \"All\"\n" +
                "              ],\n" +
                "              \"osId\": \"org.ekstep.quiz.app\",\n" +
                "              \"owner\": \"EkStep\",\n" +
                "              \"pkgVersion\": 4,\n" +
                "              \"size\": 20283185,\n" +
                "              \"status\": \"Live\",\n" +
                "              \"tags\": [\n" +
                "                \"LP_functionalTest\"\n" +
                "              ],\n" +
                "              \"visibility\": \"Default\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"ageGroup\": [\n" +
                "                \"5-6\"\n" +
                "              ],\n" +
                "              \"artifactUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/the_moon_and_the_cap_1472540773787.zip\",\n" +
                "              \"code\": \"Test_QA\",\n" +
                "              \"contentType\": \"Story\",\n" +
                "              \"createdOn\": \"2016-08-30T07:06:08.944+0000\",\n" +
                "              \"description\": \"Test_QA\",\n" +
                "              \"downloadUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/test_qat-955_1472540780034_test_qat_955.ecar\",\n" +
                "              \"es_metadata_id\": \"Test_QAT_955\",\n" +
                "              \"gradeLevel\": [\n" +
                "                \"Grade 1\"\n" +
                "              ],\n" +
                "              \"graph_id\": \"domain\",\n" +
                "              \"idealScreenDensity\": \"hdpi\",\n" +
                "              \"idealScreenSize\": \"normal\",\n" +
                "              \"identifier\": \"Test_QAT_955\",\n" +
                "              \"language\": [\n" +
                "                \"English\"\n" +
                "              ],\n" +
                "              \"lastPublishedOn\": \"2016-08-30T07:06:23.741+0000\",\n" +
                "              \"lastUpdatedOn\": \"2016-08-30T07:06:23.817+0000\",\n" +
                "              \"license\": \"Creative Commons Attribution (CC BY)\",\n" +
                "              \"mediaType\": \"content\",\n" +
                "              \"mimeType\": \"application/vnd.ekstep.ecml-archive\",\n" +
                "              \"name\": \"Test_QAT-955\",\n" +
                "              \"nodeType\": \"DATA_NODE\",\n" +
                "              \"node_id\": 45986,\n" +
                "              \"objectType\": \"Content\",\n" +
                "              \"os\": [\n" +
                "                \"All\"\n" +
                "              ],\n" +
                "              \"osId\": \"org.ekstep.quiz.app\",\n" +
                "              \"owner\": \"EkStep\",\n" +
                "              \"pkgVersion\": 4,\n" +
                "              \"size\": 27500914,\n" +
                "              \"status\": \"Live\",\n" +
                "              \"tags\": [\n" +
                "                \"LP_functionalTest\"\n" +
                "              ],\n" +
                "              \"visibility\": \"Default\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"ageGroup\": [\n" +
                "                \"5-6\"\n" +
                "              ],\n" +
                "              \"artifactUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/haircut_story_1472540762395.zip\",\n" +
                "              \"code\": \"Test_QA\",\n" +
                "              \"contentType\": \"Story\",\n" +
                "              \"createdOn\": \"2016-08-30T07:05:58.146+0000\",\n" +
                "              \"description\": \"Test_QA\",\n" +
                "              \"downloadUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/test_qat-1007_1472540765760_test_qat_1007.ecar\",\n" +
                "              \"es_metadata_id\": \"Test_QAT_1007\",\n" +
                "              \"gradeLevel\": [\n" +
                "                \"Grade 1\"\n" +
                "              ],\n" +
                "              \"graph_id\": \"domain\",\n" +
                "              \"idealScreenDensity\": \"hdpi\",\n" +
                "              \"idealScreenSize\": \"normal\",\n" +
                "              \"identifier\": \"Test_QAT_1007\",\n" +
                "              \"language\": [\n" +
                "                \"English\"\n" +
                "              ],\n" +
                "              \"lastPublishedOn\": \"2016-08-30T07:06:08.457+0000\",\n" +
                "              \"lastUpdatedOn\": \"2016-08-30T07:06:08.523+0000\",\n" +
                "              \"license\": \"Creative Commons Attribution (CC BY)\",\n" +
                "              \"mediaType\": \"content\",\n" +
                "              \"mimeType\": \"application/vnd.ekstep.ecml-archive\",\n" +
                "              \"name\": \"Test_QAT-1007\",\n" +
                "              \"nodeType\": \"DATA_NODE\",\n" +
                "              \"node_id\": 45985,\n" +
                "              \"objectType\": \"Content\",\n" +
                "              \"os\": [\n" +
                "                \"All\"\n" +
                "              ],\n" +
                "              \"osId\": \"org.ekstep.quiz.app\",\n" +
                "              \"owner\": \"EkStep\",\n" +
                "              \"pkgVersion\": 4,\n" +
                "              \"size\": 20283187,\n" +
                "              \"status\": \"Live\",\n" +
                "              \"tags\": [\n" +
                "                \"LP_functionalTest\"\n" +
                "              ],\n" +
                "              \"visibility\": \"Default\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"ageGroup\": [\n" +
                "                \"5-6\"\n" +
                "              ],\n" +
                "              \"appIcon\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/14630654680611_1463032616275.thumb.png\",\n" +
                "              \"artifactUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/1472470450925_do_20045853.zip\",\n" +
                "              \"code\": \"org.ekstep.literacy.story.1937\",\n" +
                "              \"collaborators\": [\n" +
                "                \"382\",\n" +
                "                \"396\"\n" +
                "              ],\n" +
                "              \"collections\": [\n" +
                "                \"do_20045885\"\n" +
                "              ],\n" +
                "              \"concepts\": [\n" +
                "                \"LO37\",\n" +
                "                \"LO39\",\n" +
                "                \"LO47\",\n" +
                "                \"LO44\"\n" +
                "              ],\n" +
                "              \"contentType\": \"Story\",\n" +
                "              \"copyright\": \"\",\n" +
                "              \"createdOn\": \"2016-08-29T04:34:24.472+0000\",\n" +
                "              \"description\": \"Test\",\n" +
                "              \"domain\": [\n" +
                "                \"literacy\"\n" +
                "              ],\n" +
                "              \"downloadUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/test2982016-01_1472472825736_do_20045853.ecar\",\n" +
                "              \"es_metadata_id\": \"do_20045853\",\n" +
                "              \"gradeLevel\": [\n" +
                "                \"Kindergarten\",\n" +
                "                \"Grade 1\",\n" +
                "                \"Grade 2\",\n" +
                "                \"Grade 3\",\n" +
                "                \"Grade 4\",\n" +
                "                \"Grade 5\",\n" +
                "                \"Other\"\n" +
                "              ],\n" +
                "              \"graph_id\": \"domain\",\n" +
                "              \"idealScreenDensity\": \"hdpi\",\n" +
                "              \"idealScreenSize\": \"normal\",\n" +
                "              \"identifier\": \"do_20045853\",\n" +
                "              \"imageCredits\": [\n" +
                "                \"ekstep\",\n" +
                "                \"EkStep\"\n" +
                "              ],\n" +
                "              \"language\": [\n" +
                "                \"English\"\n" +
                "              ],\n" +
                "              \"lastPublishedOn\": \"2016-08-29T12:13:46.722+0000\",\n" +
                "              \"lastSubmittedOn\": \"2016-08-29T11:47:39.163+0000\",\n" +
                "              \"lastUpdatedBy\": \"393\",\n" +
                "              \"lastUpdatedOn\": \"2016-08-29T12:15:30.892+0000\",\n" +
                "              \"license\": \"Creative Commons Attribution (CC BY)\",\n" +
                "              \"mediaType\": \"content\",\n" +
                "              \"mimeType\": \"application/vnd.ekstep.ecml-archive\",\n" +
                "              \"name\": \"Test2982016-01\",\n" +
                "              \"nodeType\": \"DATA_NODE\",\n" +
                "              \"node_id\": 45853,\n" +
                "              \"objectType\": \"Content\",\n" +
                "              \"os\": [\n" +
                "                \"All\"\n" +
                "              ],\n" +
                "              \"osId\": \"org.ekstep.quiz.app\",\n" +
                "              \"owner\": \"SQS Ekstep\",\n" +
                "              \"pkgVersion\": 1,\n" +
                "              \"portalOwner\": \"393\",\n" +
                "              \"posterImage\": \"https://qa.ekstep.in/assets/public/content/14630654680611_1463032616275.png\",\n" +
                "              \"publisher\": \"\",\n" +
                "              \"size\": 5338937,\n" +
                "              \"soundCredits\": [\n" +
                "                \"ekstep\"\n" +
                "              ],\n" +
                "              \"source\": \"\",\n" +
                "              \"status\": \"Live\",\n" +
                "              \"visibility\": \"Default\"\n" +
                "            }\n" +
                "          ]\n" +
                "        },\n" +
                "        {\n" +
                "          \"display\": {\n" +
                "            \"name\": {\n" +
                "              \"en\": \"Top Stories\",\n" +
                "              \"hn\": \"लोकप्रिय कहानियां\"\n" +
                "            }\n" +
                "          },\n" +
                "          \"search\": {\n" +
                "            \"query\": \"\",\n" +
                "            \"limit\": 10,\n" +
                "            \"sort_by\": {\n" +
                "              \"popularity\": \"desc\"\n" +
                "            },\n" +
                "            \"filters\": {\n" +
                "              \"contentType\": [\n" +
                "                \"Story\"\n" +
                "              ],\n" +
                "              \"objectType\": [\n" +
                "                \"Content\"\n" +
                "              ],\n" +
                "              \"status\": [\n" +
                "                \"Live\"\n" +
                "              ]\n" +
                "            },\n" +
                "            \"facets\": [\n" +
                "              \"contentType\",\n" +
                "              \"domain\",\n" +
                "              \"ageGroup\",\n" +
                "              \"language\",\n" +
                "              \"gradeLevel\"\n" +
                "            ]\n" +
                "          },\n" +
                "          \"recommend\": null,\n" +
                "          \"contents\": [\n" +
                "            {\n" +
                "              \"ageGroup\": [\n" +
                "                \"5-6\",\n" +
                "                \"6-7\",\n" +
                "                \"7-8\"\n" +
                "              ],\n" +
                "              \"appIcon\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/language_assets/146166842111696ed5e8e47-1465900163013_1467801696089.jpg\",\n" +
                "              \"artifactUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/1468411695040_do_30036497.zip\",\n" +
                "              \"code\": \"org.ekstep.literacy.story.815\",\n" +
                "              \"concepts\": [\n" +
                "                \"LO46\"\n" +
                "              ],\n" +
                "              \"contentType\": \"Story\",\n" +
                "              \"copyright\": \"Pratham Books\",\n" +
                "              \"createdOn\": \"2016-07-06T10:31:01.875+0000\",\n" +
                "              \"description\": \"One day Maaloo had to get some potatoes from his kitchen garden. Who do you think helped him? Kaaloo, of course! Read about Maaloo's morning with Kaaloo as they found some 'Aaloo'! Children can read and listen to the story. This story is appropriate for children who starting to read sentences.\",\n" +
                "              \"domain\": [\n" +
                "                \"literacy\"\n" +
                "              ],\n" +
                "              \"downloadUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/do_30036497_1468411698411.ecar\",\n" +
                "              \"es_metadata_id\": \"do_30036497\",\n" +
                "              \"gradeLevel\": [\n" +
                "                \"Kindergarten\",\n" +
                "                \"Grade 1\",\n" +
                "                \"Grade 2\"\n" +
                "              ],\n" +
                "              \"graph_id\": \"domain\",\n" +
                "              \"idealScreenDensity\": \"hdpi\",\n" +
                "              \"idealScreenSize\": \"normal\",\n" +
                "              \"identifier\": \"do_30036497\",\n" +
                "              \"imageCredits\": [\n" +
                "                \"ekstep\",\n" +
                "                \"Parabal Partap Singh\",\n" +
                "                \"Pratham Books\"\n" +
                "              ],\n" +
                "              \"language\": [\n" +
                "                \"English\"\n" +
                "              ],\n" +
                "              \"lastPublishedOn\": \"2016-07-13T12:08:19.467+0000\",\n" +
                "              \"lastSubmittedOn\": \"2016-07-06T14:10:25.638+0000\",\n" +
                "              \"lastUpdatedBy\": \"80\",\n" +
                "              \"lastUpdatedOn\": \"2016-07-13T12:08:20.127+0000\",\n" +
                "              \"license\": \"Creative Commons Attribution (CC BY)\",\n" +
                "              \"mediaType\": \"content\",\n" +
                "              \"mimeType\": \"application/vnd.ekstep.ecml-archive\",\n" +
                "              \"name\": \"Aaloo-Maaloo-Kaaloo\",\n" +
                "              \"nodeType\": \"DATA_NODE\",\n" +
                "              \"node_id\": 36497,\n" +
                "              \"objectType\": \"Content\",\n" +
                "              \"os\": [\n" +
                "                \"All\"\n" +
                "              ],\n" +
                "              \"osId\": \"org.ekstep.quiz.app\",\n" +
                "              \"owner\": \"Vinita Krishna\",\n" +
                "              \"pkgVersion\": 5,\n" +
                "              \"portalOwner\": \"334\",\n" +
                "              \"publisher\": \"Pratham Books\",\n" +
                "              \"size\": 7433551,\n" +
                "              \"soundCredits\": [\n" +
                "                \"ekstep\"\n" +
                "              ],\n" +
                "              \"source\": \"MangoReader\",\n" +
                "              \"status\": \"Live\",\n" +
                "              \"tags\": [\n" +
                "                \"prathamdigitalcamal\",\n" +
                "                \"friends\",\n" +
                "                \"pratham books\",\n" +
                "                \"courage\",\n" +
                "                \"nature\",\n" +
                "                \"fun\",\n" +
                "                \"mangosense\"\n" +
                "              ],\n" +
                "              \"visibility\": \"Default\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"ageGroup\": [\n" +
                "                \"5-6\"\n" +
                "              ],\n" +
                "              \"appIcon\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/adjective_347_1468566136_1468566172028.thumb.png\",\n" +
                "              \"artifactUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/pratham1_347_1470910567_1470910651557.zip\",\n" +
                "              \"code\": \"org.ekstep.literacy.story.1211\",\n" +
                "              \"concepts\": [\n" +
                "                \"LO52\"\n" +
                "              ],\n" +
                "              \"contentType\": \"Story\",\n" +
                "              \"copyright\": \"\",\n" +
                "              \"createdOn\": \"2016-07-15T06:56:57.362+0000\",\n" +
                "              \"description\": \"test_prathamstoriesfirstday\",\n" +
                "              \"domain\": [\n" +
                "                \"literacy\"\n" +
                "              ],\n" +
                "              \"downloadUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/test_prathamstoriesfirstday_1470910766701_do_20043357.ecar\",\n" +
                "              \"es_metadata_id\": \"do_20043357\",\n" +
                "              \"gradeLevel\": [\n" +
                "                \"Kindergarten\",\n" +
                "                \"Grade 1\",\n" +
                "                \"Grade 2\",\n" +
                "                \"Grade 3\",\n" +
                "                \"Grade 4\",\n" +
                "                \"Grade 5\",\n" +
                "                \"Other\"\n" +
                "              ],\n" +
                "              \"graph_id\": \"domain\",\n" +
                "              \"idealScreenDensity\": \"hdpi\",\n" +
                "              \"idealScreenSize\": \"normal\",\n" +
                "              \"identifier\": \"do_20043357\",\n" +
                "              \"language\": [\n" +
                "                \"English\"\n" +
                "              ],\n" +
                "              \"lastPublishedOn\": \"2016-08-11T10:19:29.789+0000\",\n" +
                "              \"lastSubmittedOn\": \"2016-08-11T10:18:02.189+0000\",\n" +
                "              \"lastUpdatedBy\": \"331\",\n" +
                "              \"lastUpdatedOn\": \"2016-08-11T10:19:29.964+0000\",\n" +
                "              \"license\": \"Creative Commons Attribution (CC BY)\",\n" +
                "              \"mediaType\": \"content\",\n" +
                "              \"mimeType\": \"application/vnd.ekstep.ecml-archive\",\n" +
                "              \"name\": \"test_prathamstoriesfirstday\",\n" +
                "              \"nodeType\": \"DATA_NODE\",\n" +
                "              \"node_id\": 43357,\n" +
                "              \"objectType\": \"Content\",\n" +
                "              \"os\": [\n" +
                "                \"All\"\n" +
                "              ],\n" +
                "              \"osId\": \"org.ekstep.quiz.app\",\n" +
                "              \"owner\": \"Debasis Singh\",\n" +
                "              \"pkgVersion\": 6,\n" +
                "              \"portalOwner\": \"347\",\n" +
                "              \"posterImage\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/language_assets/adjective_347_1468566136_1468566172028.png\",\n" +
                "              \"publisher\": \"\",\n" +
                "              \"size\": 11906723,\n" +
                "              \"source\": \"\",\n" +
                "              \"status\": \"Live\",\n" +
                "              \"visibility\": \"Default\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"ageGroup\": [\n" +
                "                \"5-6\"\n" +
                "              ],\n" +
                "              \"artifactUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/isaksham_latest_200_1468847281_1468847322583.zip\",\n" +
                "              \"code\": \"org.ekstep.literacy.story.1268\",\n" +
                "              \"concepts\": [\n" +
                "                \"LO1\"\n" +
                "              ],\n" +
                "              \"contentType\": \"Story\",\n" +
                "              \"copyright\": \"\",\n" +
                "              \"createdOn\": \"2016-07-18T12:45:04.668+0000\",\n" +
                "              \"description\": \"iSaksham_4\",\n" +
                "              \"domain\": [\n" +
                "                \"literacy\"\n" +
                "              ],\n" +
                "              \"downloadUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/isaksham_4_1468847344919_do_20043422.ecar\",\n" +
                "              \"es_metadata_id\": \"do_20043422\",\n" +
                "              \"gradeLevel\": [\n" +
                "                \"Kindergarten\",\n" +
                "                \"Grade 1\",\n" +
                "                \"Grade 2\",\n" +
                "                \"Grade 3\",\n" +
                "                \"Grade 4\",\n" +
                "                \"Grade 5\",\n" +
                "                \"Other\"\n" +
                "              ],\n" +
                "              \"graph_id\": \"domain\",\n" +
                "              \"idealScreenDensity\": \"hdpi\",\n" +
                "              \"idealScreenSize\": \"normal\",\n" +
                "              \"identifier\": \"do_20043422\",\n" +
                "              \"language\": [\n" +
                "                \"English\"\n" +
                "              ],\n" +
                "              \"lastPublishedOn\": \"2016-07-18T13:09:05.832+0000\",\n" +
                "              \"lastSubmittedOn\": \"2016-07-18T13:08:53.406+0000\",\n" +
                "              \"lastUpdatedBy\": \"347\",\n" +
                "              \"lastUpdatedOn\": \"2016-07-18T13:09:05.983+0000\",\n" +
                "              \"license\": \"Creative Commons Attribution (CC BY)\",\n" +
                "              \"mediaType\": \"content\",\n" +
                "              \"mimeType\": \"application/vnd.ekstep.ecml-archive\",\n" +
                "              \"name\": \"iSaksham_4\",\n" +
                "              \"nodeType\": \"DATA_NODE\",\n" +
                "              \"node_id\": 43422,\n" +
                "              \"objectType\": \"Content\",\n" +
                "              \"os\": [\n" +
                "                \"All\"\n" +
                "              ],\n" +
                "              \"osId\": \"org.ekstep.quiz.app\",\n" +
                "              \"owner\": \"Srivathsa Dhanraj\",\n" +
                "              \"pkgVersion\": 1,\n" +
                "              \"portalOwner\": \"200\",\n" +
                "              \"publisher\": \"\",\n" +
                "              \"size\": 5609434,\n" +
                "              \"source\": \"\",\n" +
                "              \"status\": \"Live\",\n" +
                "              \"visibility\": \"Default\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"ageGroup\": [\n" +
                "                \"5-6\"\n" +
                "              ],\n" +
                "              \"artifactUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/haircut_story_1470203665399.zip\",\n" +
                "              \"code\": \"Test_QA\",\n" +
                "              \"contentType\": \"Story\",\n" +
                "              \"createdOn\": \"2016-08-03T05:54:10.599+0000\",\n" +
                "              \"description\": \"Test_QA\",\n" +
                "              \"downloadUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/test_qat-733_1470203669049_test_qat_733.ecar\",\n" +
                "              \"es_metadata_id\": \"Test_QAT_733\",\n" +
                "              \"gradeLevel\": [\n" +
                "                \"Grade 1\"\n" +
                "              ],\n" +
                "              \"graph_id\": \"domain\",\n" +
                "              \"idealScreenDensity\": \"hdpi\",\n" +
                "              \"idealScreenSize\": \"normal\",\n" +
                "              \"identifier\": \"Test_QAT_733\",\n" +
                "              \"language\": [\n" +
                "                \"English\"\n" +
                "              ],\n" +
                "              \"lastPublishedOn\": \"2016-08-03T05:54:31.749+0000\",\n" +
                "              \"lastUpdatedOn\": \"2016-08-03T05:54:31.836+0000\",\n" +
                "              \"license\": \"Creative Commons Attribution (CC BY)\",\n" +
                "              \"mediaType\": \"content\",\n" +
                "              \"mimeType\": \"application/vnd.ekstep.ecml-archive\",\n" +
                "              \"name\": \"Test_QAT-733\",\n" +
                "              \"nodeType\": \"DATA_NODE\",\n" +
                "              \"node_id\": 44360,\n" +
                "              \"objectType\": \"Content\",\n" +
                "              \"os\": [\n" +
                "                \"All\"\n" +
                "              ],\n" +
                "              \"osId\": \"org.ekstep.quiz.app\",\n" +
                "              \"owner\": \"EkStep\",\n" +
                "              \"pkgVersion\": 4,\n" +
                "              \"size\": 20283662,\n" +
                "              \"status\": \"Live\",\n" +
                "              \"visibility\": \"Default\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"ageGroup\": [\n" +
                "                \"5-6\"\n" +
                "              ],\n" +
                "              \"artifactUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/1468306276413_do_20043181.zip\",\n" +
                "              \"code\": \"org.ekstep.literacy.story.1072\",\n" +
                "              \"concepts\": [\n" +
                "                \"LO51\",\n" +
                "                \"LO7\"\n" +
                "              ],\n" +
                "              \"contentType\": \"Story\",\n" +
                "              \"copyright\": \"\",\n" +
                "              \"createdOn\": \"2016-07-12T06:46:19.326+0000\",\n" +
                "              \"description\": \"zoomanners_story_part1\",\n" +
                "              \"domain\": [\n" +
                "                \"literacy\"\n" +
                "              ],\n" +
                "              \"downloadUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/do_20043181_1468306276531.ecar\",\n" +
                "              \"es_metadata_id\": \"do_20043181\",\n" +
                "              \"gradeLevel\": [\n" +
                "                \"Kindergarten\",\n" +
                "                \"Grade 1\",\n" +
                "                \"Grade 2\",\n" +
                "                \"Grade 3\",\n" +
                "                \"Grade 4\",\n" +
                "                \"Grade 5\",\n" +
                "                \"Other\"\n" +
                "              ],\n" +
                "              \"graph_id\": \"domain\",\n" +
                "              \"idealScreenDensity\": \"hdpi\",\n" +
                "              \"idealScreenSize\": \"normal\",\n" +
                "              \"identifier\": \"do_20043181\",\n" +
                "              \"language\": [\n" +
                "                \"English\"\n" +
                "              ],\n" +
                "              \"lastPublishedOn\": \"2016-07-12T06:51:16.666+0000\",\n" +
                "              \"lastSubmittedOn\": \"2016-07-12T06:49:19.444+0000\",\n" +
                "              \"lastUpdatedBy\": \"200\",\n" +
                "              \"lastUpdatedOn\": \"2016-07-12T06:51:16.829+0000\",\n" +
                "              \"license\": \"Creative Commons Attribution (CC BY)\",\n" +
                "              \"mediaType\": \"content\",\n" +
                "              \"mimeType\": \"application/vnd.ekstep.ecml-archive\",\n" +
                "              \"name\": \"zoomanners_story_part1\",\n" +
                "              \"nodeType\": \"DATA_NODE\",\n" +
                "              \"node_id\": 43181,\n" +
                "              \"objectType\": \"Content\",\n" +
                "              \"os\": [\n" +
                "                \"All\"\n" +
                "              ],\n" +
                "              \"osId\": \"org.ekstep.quiz.app\",\n" +
                "              \"owner\": \"George E\",\n" +
                "              \"pkgVersion\": 1,\n" +
                "              \"portalOwner\": \"371\",\n" +
                "              \"publisher\": \"\",\n" +
                "              \"size\": 1100,\n" +
                "              \"source\": \"\",\n" +
                "              \"status\": \"Live\",\n" +
                "              \"visibility\": \"Default\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"ageGroup\": [\n" +
                "                \"5-6\"\n" +
                "              ],\n" +
                "              \"appIcon\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/adjective_347_1468566837_1468566872504.thumb.png\",\n" +
                "              \"artifactUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/prathamstory4_347_1470910273_1470910357813.zip\",\n" +
                "              \"code\": \"org.ekstep.literacy.story.1218\",\n" +
                "              \"concepts\": [\n" +
                "                \"LO52\"\n" +
                "              ],\n" +
                "              \"contentType\": \"Story\",\n" +
                "              \"copyright\": \"\",\n" +
                "              \"createdOn\": \"2016-07-15T07:13:47.488+0000\",\n" +
                "              \"description\": \"test_prathamstoriesfourthday\",\n" +
                "              \"domain\": [\n" +
                "                \"literacy\"\n" +
                "              ],\n" +
                "              \"downloadUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/test_prathamstoriesfourthday_1470910437324_do_20043364.ecar\",\n" +
                "              \"es_metadata_id\": \"do_20043364\",\n" +
                "              \"gradeLevel\": [\n" +
                "                \"Kindergarten\",\n" +
                "                \"Grade 1\",\n" +
                "                \"Grade 2\",\n" +
                "                \"Grade 3\",\n" +
                "                \"Grade 4\",\n" +
                "                \"Grade 5\",\n" +
                "                \"Other\"\n" +
                "              ],\n" +
                "              \"graph_id\": \"domain\",\n" +
                "              \"idealScreenDensity\": \"hdpi\",\n" +
                "              \"idealScreenSize\": \"normal\",\n" +
                "              \"identifier\": \"do_20043364\",\n" +
                "              \"language\": [\n" +
                "                \"English\"\n" +
                "              ],\n" +
                "              \"lastPublishedOn\": \"2016-08-11T10:13:59.592+0000\",\n" +
                "              \"lastSubmittedOn\": \"2016-08-11T10:13:13.284+0000\",\n" +
                "              \"lastUpdatedBy\": \"331\",\n" +
                "              \"lastUpdatedOn\": \"2016-08-11T10:13:59.814+0000\",\n" +
                "              \"license\": \"Creative Commons Attribution (CC BY)\",\n" +
                "              \"mediaType\": \"content\",\n" +
                "              \"mimeType\": \"application/vnd.ekstep.ecml-archive\",\n" +
                "              \"name\": \"test_prathamstoriesfourthday\",\n" +
                "              \"nodeType\": \"DATA_NODE\",\n" +
                "              \"node_id\": 43364,\n" +
                "              \"objectType\": \"Content\",\n" +
                "              \"os\": [\n" +
                "                \"All\"\n" +
                "              ],\n" +
                "              \"osId\": \"org.ekstep.quiz.app\",\n" +
                "              \"owner\": \"Debasis Singh\",\n" +
                "              \"pkgVersion\": 9,\n" +
                "              \"portalOwner\": \"347\",\n" +
                "              \"posterImage\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/language_assets/adjective_347_1468566837_1468566872504.png\",\n" +
                "              \"publisher\": \"\",\n" +
                "              \"size\": 5376842,\n" +
                "              \"source\": \"\",\n" +
                "              \"status\": \"Live\",\n" +
                "              \"visibility\": \"Default\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"ageGroup\": [\n" +
                "                \"5-6\"\n" +
                "              ],\n" +
                "              \"artifactUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/externaljsonitemdatacdata_1470284037512.zip\",\n" +
                "              \"code\": \"Test_QA\",\n" +
                "              \"contentType\": \"Story\",\n" +
                "              \"createdOn\": \"2016-08-04T04:13:48.806+0000\",\n" +
                "              \"description\": \"Test_QA\",\n" +
                "              \"downloadUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/test_qa_2191396_1470284042095_test_qa_2191396.ecar\",\n" +
                "              \"es_metadata_id\": \"Test_QA_2191396\",\n" +
                "              \"gradeLevel\": [\n" +
                "                \"Grade 1\"\n" +
                "              ],\n" +
                "              \"graph_id\": \"domain\",\n" +
                "              \"idealScreenDensity\": \"hdpi\",\n" +
                "              \"idealScreenSize\": \"normal\",\n" +
                "              \"identifier\": \"Test_QA_2191396\",\n" +
                "              \"language\": [\n" +
                "                \"English\"\n" +
                "              ],\n" +
                "              \"lastPublishedOn\": \"2016-08-04T04:14:05.583+0000\",\n" +
                "              \"lastUpdatedOn\": \"2016-08-04T04:14:05.639+0000\",\n" +
                "              \"license\": \"Creative Commons Attribution (CC BY)\",\n" +
                "              \"mediaType\": \"content\",\n" +
                "              \"mimeType\": \"application/vnd.ekstep.ecml-archive\",\n" +
                "              \"name\": \"Test_QA_2191396\",\n" +
                "              \"nodeType\": \"DATA_NODE\",\n" +
                "              \"node_id\": 44520,\n" +
                "              \"objectType\": \"Content\",\n" +
                "              \"os\": [\n" +
                "                \"All\"\n" +
                "              ],\n" +
                "              \"osId\": \"org.ekstep.quiz.app\",\n" +
                "              \"owner\": \"EkStep\",\n" +
                "              \"pkgVersion\": 4,\n" +
                "              \"size\": 27488612,\n" +
                "              \"status\": \"Live\",\n" +
                "              \"visibility\": \"Default\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"ageGroup\": [\n" +
                "                \"5-6\"\n" +
                "              ],\n" +
                "              \"artifactUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/the_moon_and_the_cap_1470290129650.zip\",\n" +
                "              \"code\": \"Test_QA\",\n" +
                "              \"collections\": [\n" +
                "                \"Test_QA_Collection2983923\"\n" +
                "              ],\n" +
                "              \"contentType\": \"Story\",\n" +
                "              \"createdOn\": \"2016-08-04T05:55:18.656+0000\",\n" +
                "              \"description\": \"Test_QA\",\n" +
                "              \"downloadUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/test_qat-646_1470290135262_test_qat_646.ecar\",\n" +
                "              \"es_metadata_id\": \"Test_QAT_646\",\n" +
                "              \"gradeLevel\": [\n" +
                "                \"Grade 1\"\n" +
                "              ],\n" +
                "              \"graph_id\": \"domain\",\n" +
                "              \"idealScreenDensity\": \"hdpi\",\n" +
                "              \"idealScreenSize\": \"normal\",\n" +
                "              \"identifier\": \"Test_QAT_646\",\n" +
                "              \"language\": [\n" +
                "                \"English\"\n" +
                "              ],\n" +
                "              \"lastPublishedOn\": \"2016-08-04T05:55:38.843+0000\",\n" +
                "              \"lastUpdatedOn\": \"2016-08-04T05:55:38.910+0000\",\n" +
                "              \"license\": \"Creative Commons Attribution (CC BY)\",\n" +
                "              \"mediaType\": \"content\",\n" +
                "              \"mimeType\": \"application/vnd.ekstep.ecml-archive\",\n" +
                "              \"name\": \"Test_QAT-646\",\n" +
                "              \"nodeType\": \"DATA_NODE\",\n" +
                "              \"node_id\": 44550,\n" +
                "              \"objectType\": \"Content\",\n" +
                "              \"os\": [\n" +
                "                \"All\"\n" +
                "              ],\n" +
                "              \"osId\": \"org.ekstep.quiz.app\",\n" +
                "              \"owner\": \"EkStep\",\n" +
                "              \"pkgVersion\": 4,\n" +
                "              \"size\": 27500899,\n" +
                "              \"status\": \"Live\",\n" +
                "              \"visibility\": \"Default\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"ageGroup\": [\n" +
                "                \"5-6\"\n" +
                "              ],\n" +
                "              \"artifactUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/ecml_without_asset_1470294356676.zip\",\n" +
                "              \"code\": \"Test_QA\",\n" +
                "              \"contentType\": \"Story\",\n" +
                "              \"createdOn\": \"2016-08-04T07:05:56.107+0000\",\n" +
                "              \"description\": \"Test_QA\",\n" +
                "              \"downloadUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/test_qa_4183482_1470294359428_test_qa_4183482.ecar\",\n" +
                "              \"es_metadata_id\": \"Test_QA_4183482\",\n" +
                "              \"gradeLevel\": [\n" +
                "                \"Grade 1\"\n" +
                "              ],\n" +
                "              \"graph_id\": \"domain\",\n" +
                "              \"idealScreenDensity\": \"hdpi\",\n" +
                "              \"idealScreenSize\": \"normal\",\n" +
                "              \"identifier\": \"Test_QA_4183482\",\n" +
                "              \"language\": [\n" +
                "                \"English\"\n" +
                "              ],\n" +
                "              \"lastPublishedOn\": \"2016-08-04T07:05:59.545+0000\",\n" +
                "              \"lastUpdatedOn\": \"2016-08-04T07:05:59.615+0000\",\n" +
                "              \"license\": \"Creative Commons Attribution (CC BY)\",\n" +
                "              \"mediaType\": \"content\",\n" +
                "              \"mimeType\": \"application/vnd.ekstep.ecml-archive\",\n" +
                "              \"name\": \"Test_QA_4183482\",\n" +
                "              \"nodeType\": \"DATA_NODE\",\n" +
                "              \"node_id\": 44613,\n" +
                "              \"objectType\": \"Content\",\n" +
                "              \"os\": [\n" +
                "                \"All\"\n" +
                "              ],\n" +
                "              \"osId\": \"org.ekstep.quiz.app\",\n" +
                "              \"owner\": \"EkStep\",\n" +
                "              \"pkgVersion\": 4,\n" +
                "              \"size\": 2938,\n" +
                "              \"status\": \"Live\",\n" +
                "              \"visibility\": \"Default\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"ageGroup\": [\n" +
                "                \"5-6\"\n" +
                "              ],\n" +
                "              \"artifactUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/content/data_json_ecml_1470297795369.zip\",\n" +
                "              \"code\": \"Test_QA\",\n" +
                "              \"contentType\": \"Story\",\n" +
                "              \"createdOn\": \"2016-08-04T08:03:13.281+0000\",\n" +
                "              \"description\": \"Test_QA\",\n" +
                "              \"downloadUrl\": \"https://ekstep-public.s3-ap-southeast-1.amazonaws.com/ecar_files/test_qa_1347034_1470297804634_test_qa_1347034.ecar\",\n" +
                "              \"es_metadata_id\": \"Test_QA_1347034\",\n" +
                "              \"gradeLevel\": [\n" +
                "                \"Grade 1\"\n" +
                "              ],\n" +
                "              \"graph_id\": \"domain\",\n" +
                "              \"idealScreenDensity\": \"hdpi\",\n" +
                "              \"idealScreenSize\": \"normal\",\n" +
                "              \"identifier\": \"Test_QA_1347034\",\n" +
                "              \"language\": [\n" +
                "                \"English\"\n" +
                "              ],\n" +
                "              \"lastPublishedOn\": \"2016-08-04T08:03:25.652+0000\",\n" +
                "              \"lastUpdatedOn\": \"2016-08-04T08:03:25.730+0000\",\n" +
                "              \"license\": \"Creative Commons Attribution (CC BY)\",\n" +
                "              \"mediaType\": \"content\",\n" +
                "              \"mimeType\": \"application/vnd.ekstep.ecml-archive\",\n" +
                "              \"name\": \"Test_QA_1347034\",\n" +
                "              \"nodeType\": \"DATA_NODE\",\n" +
                "              \"node_id\": 44673,\n" +
                "              \"objectType\": \"Content\",\n" +
                "              \"os\": [\n" +
                "                \"All\"\n" +
                "              ],\n" +
                "              \"osId\": \"org.ekstep.quiz.app\",\n" +
                "              \"owner\": \"EkStep\",\n" +
                "              \"pkgVersion\": 4,\n" +
                "              \"size\": 6976414,\n" +
                "              \"status\": \"Live\",\n" +
                "              \"visibility\": \"Default\"\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  }\n" +
                "}";
    }

    public static String getLocalData(String identifier) {
        return "{\n" +
                "        \"minSupportedVersion\":null,\n" +
                "                  \"appIcon\":\"https://s3-ap-southeast-1.amazonaws.com/ekstep-public/games/1445251040215_img_logo.png\",\n" +
                "                  \"communication_scheme\":\"INTENT\",\n" +
                "                  \"subject\":\"numeracy\",\n" +
                "                  \"posterImage\":\"https://s3-ap-southeast-1.amazonaws.com/ekstep-public/games/1446717394480_img_logo.png\",\n" +
                "                  \"launchUrl\":null,\n" +
                "                  \"downloadUrl\": \"https://ekstep-public-qa.s3-ap-south-1.amazonaws.com/ecar_files/do_2121925679111454721253/nestedcollectiontest01_1488352213439_do_2121925679111454721253_3.0.ecar\",\n" +
                "                  \"code\":\"org.akshara\",\n" +
                "                  \"activity_class\":\".MainActivity\",\n" +
                "                  \"osId\":\"org.akshara\",\n" +
                "                  \"description\":\"AKSHARA PARTNER APP\",\n" +
                "                  \"name\":\"Akshara Partner App\",\n" +
                "                  \"identifier\":\"numeracy_418\",\n" +
                "                  \"grayScaleAppIcon\":\"https://s3-ap-southeast-1.amazonaws.com/ekstep-public/games/1445251051097_img_logo1.png\",\n" +
                "                  \"filter\":null,\n" +
                "                  \"contentType\":\"Worksheet\",\n" +
                "                  \"mimeType\":\"application/vnd.android.package-archive\",\n" +
                "                  \"pkgVersion\":1.2\n" +
                "            }";
    }

    public static String getPushNotification(int msgId, String time, int validity) {
        return "{\n" +
                "  \"msgid\": \"" + msgId + "\",\n" +
                "  \"title\": \"Welcome to Genie!\",\n" +
                "  \"msg\": \"Congratulations on downloading Genie, every child's best friend in their learning journey.\",\n" +
                "  \"icon\": \"\",\n" +
                "  \"time\": \"" + time + "\",\n" +
                "  \"validity\":" + validity + ",\n" +
                "  \"actiondata\": {\n" +
                "    \"contentid\": \"do_30106960\",\n" +
                "    \"contendid\": \"do_30106960\"\n" +
                "  }\n" +
                "}";
    }

    public static String getLocalNotification(int msgId, int validity) {
        return "{\n" +
                "  \"msgid\":" + msgId + ",\n" +
                "  \"title\":\"Welcome to Genie!\",\n" +
                "  \"msg\":\"Congratulations on downloading Genie, every child's best friend in their learning journey.\",\n" +
                "  \"relativetime\":" + validity + ",\n" +
                "  \"actionid\":1\n" +
                "}";
    }
}
