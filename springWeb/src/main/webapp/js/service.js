/*
 针对机器人客服UI相关的函数
 */

var contextPath = location.pathname.replace('/', '').replace(/\/.+/, '');
var chatUrl = '/' + contextPath + '/talk';
var clearSessionUrl = '/' + contextPath + '/clear-session';
$(function() {

    String.prototype.format = function() {
        var args = arguments;
        return this.replace(/\{\{|\}\}|\{(\d+)\}/g, function(m, n) {
            if (m == "{{") {
                return "{";
            }
            if (m == "}}") {
                return "}";
            }
            return args[n];
        });
    };

    function robotService() {
        var self = this;
        //右侧的宽
        self.right_width = 220,
            //头部的高
            self.top_height = 60,
            //发送框的高
            self.send_height = 100,
            //最小拖动宽
            self.min_width = 850,
            //最小拖动高
            self.min_height = 250;
        //客服UI的嵌套容器
        self.panel = $("#panel");
        //左侧聊天信息和发送信息对象
        self._mainleft = $("#main-left");
        //左侧发送信息对象
        self._gridbox = $("#grid-box");
        //允许输入的文字数
        self.str_count = 30;

        /*
         可视窗口的自适应
         */
        this.serviceResize = function(act) {
            //可视宽
            var width = $(window).width(),
            //可视高
                height = $(window).height(),
            //左侧的宽
                left_width = width - self.right_width,
            //左侧的高度
                left_height = height - self.top_height,
            //信息内容的高度
                box_height = left_height - self.send_height;

            //改变宽高
            if (width >= self.min_width) {
                self._mainleft.width(left_width + "px");
            }
            if (height >= self.min_height) {
                self._mainleft.height(left_height + "px");
                self._gridbox.height(box_height + "px");
            }
            if (act == "1") {
                //显示容器
                self.panel.fadeIn();
            }
        }

        /*
         限制输入字数
         */
        this.strLimit = function(obj) {
            if (obj.value.length > self.str_count)
                obj.value = obj.value.substring(0, self.str_count);
            else {
                $("#inputcount").html(self.str_count - obj.value.length);
            }
        }

        /*
         注册事件
         */
        this.serviceEvent = function() {

            window.onunload = function() {
                $.get(clearSessionUrl, function(data) {

                });
            }

            //注册Windows的Resize事件
            window.onresize = function() {
                self.serviceResize();
            }

            //限制用户输入字数
            $("#talk-input").bind("keyup change ", function(event) {
                self.strLimit(this);
            });

            $("#talk-input").bind("keydown", function(event) {
                self.strLimit(this);
                if (event.keyCode == 13) {
                    $('#chat-btn').click();
                }
            });

            var chatHistoryTemplate = '<div class="grid-talkf">' +
                '<div class="col-main">' +
                '<div class="msg-box">' +
                '<div class="msg"> ' +
                '<span class="arrow"></span>' +
                '<div class="J_content">$content$</div>' +
                '</div>' +
                '   <div class="time">$time$</div>' +
                '</div>' +
                '</div>' +
                '<div class="info-user">' +
                '<img src="images/logo_2.gif">' +
                '</div>' +
                '</div>';

            var responseHistoryTemplate = '<div class="grid-talk">' +
                '<div class="col-main">' +
                '<div class="msg-box">' +
                '<div class="msg">' +
                '<span class="arrow"></span>' +
                '<div class="J_content">' +
                '$content$' +
                '<ul class="fun">' +
                '<li class="bad"><a href="#"><s></s>没</a></li>' +
                '<li class="good"><a href="#"><s></s>有</a></li>' +
                '<li>以上内容对您有帮助吗？</li>' +
                '</ul>' +
                '</div>' +
                '</div>' +
                '<div class="time">$time$</div>' +
                '</div>' +
                '</div>' +
                '<div class="info-user">' +
                '<img src="images/logo_1.gif">' +
                '</div>' +
                '</div>';

            var responseForReviewTemplate = '<div class="grid-talk">' +
                '<div class="col-main">' +
                '<div class="msg-box">' +
                '<div class="msg">' +
                '<span class="arrow"></span>' +
                '<div class="J_content">' +
                '$content$' +
                '</div>' +
                '</div>' +
                '<div class="time">$time$</div>' +
                '</div>' +
                '</div>' +
                '<div class="info-user">' +
                '<img src="images/logo_1.gif">' +
                '</div>' +
                '</div>';

            var ticketInfoTable = "<table width=\"600px\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"cxjg\">" +
                "<tr class=\"trtitle\">" +
                "<td>航班号</td>" +
                "<td>票价</td>" +
                "<td>起飞时间</td>" +
                "<td>到达时间</td>" +
                "<td>起飞机场</td>" +
                "<td>到达机场</td>" +
                "</tr>{0}" +
                "</table>";

            var ticketInfoRow = "<tr>" +
                "<td align=\"center\"><a target=\"_blank\" href=\"http://www.9588.com\">{0}</a></td>" +
                "<td align=\"right\">{1}</td>" +
                "<td align=\"center\">{2}</td>" +
                "<td align=\"center\">{3}</td>" +
                "<td align=\"center\">{4}</td>" +
                "<td align=\"center\">{5}</td>" +
                "</tr>";

            var formatContent = function(template, content) {
                return template.replace('$content$', content).replace('$time$', currentTime());
            };

            var currentTime = function() {
                var date = new Date();
                return date.toDateString();
            };

            $('#main-left .grid-box').append($(formatContent(responseForReviewTemplate, $('.welcome-sentence').text())));

            var chat = function() {
                var input = $('#talk-input').val();
                $('#talk-input').val('');
                chatWithServer(input);
            };

            var chatWithServer = function(question) {
                if (question.length == 0) {
                    alert("请输入问题");
                    return;
                }
                var requestData = { input:question};
                showQuestion(question);
                refreshHistoryAnswer();
                sendQuestion(requestData);
            };

            var refreshHistoryAnswer = function() {
                $('.grid-talk').find('.fun').remove();
            };

            $('.faq-link').on('click', function() {
                $(this).ShowDiv();
            });

            var showQuestion = function(question) {
                $('#main-left .grid-box').append($(formatContent(chatHistoryTemplate, question)));
            };

            var sendQuestion = function(requestData) {
                $.get(chatUrl, requestData, function(data) {
                    var answer = getAnswer(data);
                    $('#main-left .grid-box').append($(formatContent(responseHistoryTemplate, answer)));
                    self._gridbox.autoScroll();
                });
            };

            var getAnswer = function(data) {
                if (data.userAnswerPrefix == 'no ticket info') {
                    return '建议:你查询的航班可能没有直达航班，请拨打400-818-9588人工查询。';
                }
                var answer = data.userAnswerPrefix;
                var airLines = data.airLines;
                if (airLines.length > 0) {
                    var ticketRows = '';
                    $.each(airLines, function(index) {
                        ticketRows += ticketInfoRow.format(airLines[index].flightNumber,
                            airLines[index].price,
                            airLines[index].departureDate,
                            airLines[index].arriveDate,
                            airportMapping[airLines[index].orgAirport],
                            airportMapping[airLines[index].dstAirport]
                        );
                    });
                    answer += ticketInfoTable.format(ticketRows);
                }
                answer += data.userAnswerSuffix;
                return answer;
            }

            $('#chat-btn').on('click', chat);

            var evaluate = function(evaluation, answer) {
                if (!evaluation.attr("clicked")) {
                    var evaluate = evaluation.parent();
                    evaluate.empty();
                    evaluate.append($('<li>' + answer + '</li>'));
                    evaluation.attr("clicked", "clicked");
                    self._gridbox.autoScroll();
                }
            }

            $('#grid-box').on('click', '.good', function() {
                evaluate($(this), '谢谢您的表扬,我会再接再厉！');
            });

            $('#grid-box').on('click', '.bad', function() {
                evaluate($(this), '谢谢您的意见，我会勤奋学习改进自己的不足。');
            });
        }
        //添加F5刷新事件
        $(document).keyup(function(event) {
            var keycode = event.which;
            if (keycode == 116) {
                $.get(clearSessionUrl, function() {
                });
            }
        });

        //添加关闭事件
        window.onbeforeunload = function() {
            $.get(clearSessionUrl, function() {
            });
        };
        /*
         初始化
         */
        this.serviceInit = function() {
            self.serviceResize("1");
            self.serviceEvent();
        }

        //注册文章关闭事件
        $("#a_close").click(function() {
            $("#a_Mask").remove();
            $("#tccdiv").hide();
        });

        var airportMapping = {
            PEK:"北京首都国际机场",
            NAY:"北京南苑机场",
            SHA:"上海虹桥国际机场",
            PVG:"上海浦东国际机场",
            CKG:"重庆江北国际机场",
            WXN:"重庆五桥机场",
            LYG:"重庆梁平机场",
            TSN:"天津滨海国际机场",
            HRB:"哈尔滨太平国际机场",
            NDG:"齐齐哈尔三家子国际机场",
            JMU:"佳木斯东郊国际机场",
            DQA:"大庆萨尔图机场",
            MDG:"牡丹江海浪机场",
            LDS:"伊春林都机场",
            HEK:"黑河黑河机场",
            OHE:"漠河古莲机场",
            CGQ:"长春龙嘉国际机场",
            YNJ:"延吉朝阳川国际机场",
            NBS:"长白山长白山机场",
            JIL:"吉林二台子机场",
            SHE:"沈阳桃仙国际机场",
            AOG:"鞍山腾鳌机场",
            DLC:"大连周水子国际机场",
            DDG:"丹东浪头国际机场",
            CNI:"长海大长山岛机场",
            CHG:"朝阳朝阳机场",
            JNZ:"锦州小岭子机场",
            XEN:"葫芦岛葫芦岛机场",
            TYN:"太原武宿机场",
            CIH:"长治王村机场",
            DAT:"大同怀仁机场",
            YCU:"运城关公机场",
            TNA:"济南遥墙国际机场",
            DOY:"东营永安机场",
            TAO:"青岛流亭国际机场",
            WEF:"潍坊潍坊机场",
            PNJ:"蓬莱沙河口机场",
            SUB:"泗水朱安达机场",
            JNG:"济宁曲埠机场",
            LYI:"临沂临沂机场",
            WEH:"威海大水泊国际机场",
            YNT:"烟台莱山机场",
            SJW:"石家庄正定国际机场",
            SHP:"秦皇岛山海关机场",
            TVS:"唐山三女河机场",
            XNT:"邢台褡裢机场",
            CGO:"郑州新郑国际机场",
            AYN:"安阳安阳机场",
            NNY:"南阳姜营机场",
            HDG:"邯郸马头机场",
            LYA:"洛阳北郊机场",
            HFE:"合肥萝岗国际机场",
            AQG:"安庆大龙山机场",
            TXN:"黄山屯溪机场",
            BFU:"蚌埠蚌埠机场",
            FUG:"阜阳西关机场",
            NKG:"南京禄口国际机场",
            CZX:"常州奔牛国际机场",
            NTG:"南通兴东机场",
            WUX:"无锡硕放机场",
            HIA:"淮安涟水机场",
            LYQ:"连云港白塔埠机场",
            XUZ:"徐州观音机场",
            YNZ:"盐城南洋机场",
            CTU:"成都双流国际机场",
            DAX:"达县河市机场",
            JZH:"九寨沟黄龙机场",
            KGT:"康定康定机场",
            PZI:"攀枝花保安营机场",
            XIC:"西昌青山机场",
            GYS:"广元盘龙机场",
            GHN:"广汉广汉机场",
            LZG:"泸州蓝田机场",
            NAO:"南充高坪机场",
            YBP:"宜宾菜坝机场",
            MIG:"绵阳南郊机场",
            KMG:"昆明巫家坝国际机场",
            BSD:"保山云瑞机场",
            JHG:"景洪西双版纳机场",
            LNJ:"临沧临沧机场",
            SYM:"思茅思茅机场",
            TCZ:"腾冲驼峰机场",
            DIG:"迪庆香格里拉机场",
            DLU:"大理大理机场",
            LJG:"丽江丽江机场",
            LUM:"德宏芒市机场",
            WNH:"文山普者黑机场",
            ZAT:"昭通昭通机场",
            KWE:"贵阳龙洞堡国际机场",
            AVA:"安顺黄果树机场",
            TEN:"铜仁大兴机场",
            ACX:"兴义兴义机场",
            LLB:"荔波荔波机场",
            HZH:"黎平黎平机场",
            ZYI:"遵义遵义机场",
            XIY:"西安咸阳国际机场",
            AKA:"安康五里铺机场",
            IQN:"庆阳庆阳机场",
            ENY:"延安二十里铺机场",
            HZG:"汉中西关机场",
            JXA:"鸡西兴凯湖机场",
            UYN:"榆林西沙机场",
            LHW:"兰州中川国际机场",
            DNH:"敦煌敦煌机场",
            THG:"天水天水机场",
            JJN:"嘉峪关嘉峪关机场",
            CHW:"酒泉鼎新机场",
            XNN:"西宁曹家堡机场",
            GQQ:"格尔木格尔木机场",
            WUH:"武汉天河国际机场",
            ENH:"恩施许家坪机场",
            YIH:"宜昌三峡机场",
            SHS:"荆州沙市机场",
            XFN:"襄樊刘集机场",
            CSX:"长沙黄花国际机场",
            CGD:"常德桃花源机场",
            HJJ:"怀化芷江机场",
            DYG:"张家界荷花机场",
            LLF:"永州零陵机场",
            KHN:"南昌昌北国际机场",
            KOW:"赣州黄金机场",
            KNC:"吉安吉安机场",
            JGS:"井冈山井冈山机场",
            JIU:"九江庐山机场",
            JDZ:"景德镇罗家机场",
            HGH:"杭州萧山国际机场",
            HYN:"黄岩路桥机场",
            WNZ:"温州永强机场",
            YIW:"义乌义乌机场",
            NGB:"宁波栎社国际机场",
            JUZ:"衢州衢州机场",
            HSN:"舟山朱家尖机场",
            DOC:"福州长乐国际机场",
            JJN:"泉州晋江机场",
            WUS:"武夷山武夷山机场",
            XMN:"厦门高崎国际机场",
            CAN:"广州白云国际机场",
            FUO:"佛山沙堤机场",
            ZUH:"珠海三灶机场",
            SZX:"深圳宝安国际机场",
            SWA:"汕头外砂机场",
            ZHA:"湛江新塘机场",
            MXZ:"梅县梅县机场",
            HAK:"海口美兰国际机场",
            SYX:"三亚凤凰国际机场",
            HET:"呼和浩特白塔国际机场",
            BAV:"包头海兰泡机场",
            ERL:"二连浩特塞乌苏国际机场",
            HLD:"海拉尔东山国际机场",
            WUA:"乌海乌海机场",
            XIL:"锡林浩特锡林浩特机场",
            CIF:"赤峰玉龙机场",
            DSN:"鄂尔多斯东胜机场",
            TGO:"通辽通辽机场",
            HLH:"乌兰浩特乌兰浩特机场",
            HZH:"满洲里西郊机场",
            INC:"银川河东机场",
            ZHY:"中卫香山机场",
            URC:"乌鲁木齐地窝堡国际机场",
            AKU:"阿克苏温宿机场",
            HTN:"和田和田国际机场",
            KRY:"克拉玛依克拉玛依机场",
            KRL:"库尔勒库尔勒机场",
            KCA:"库车库车机场",
            TCG:"塔城塔城机场",
            YIN:"伊宁伊宁机场",
            AAT:"阿勒泰阿勒泰机场",
            HMI:"哈密哈密机场",
            KHG:"喀什喀什机场",
            KJI:"喀纳斯喀纳斯机场",
            NLT:"那拉提那拉提机场",
            IQM:"且末且末机场",
            LXA:"拉萨贡嘎机场",
            LZY:"林芝米林机场",
            PBX:"昌都邦达机场",
            NNG:"南宁吴圩国际机场",
            AEB:"百色右江机场",
            LZH:"柳州白莲机场",
            WUZ:"梧州长洲机场",
            BHY:"北海福成机场",
            KWL:"桂林两江国际机场",
            HKG:"香港赤鱲角国际机场",
            MFM:"澳门澳门国际机场",
            TSA:"台北松山机场",
            RMQ:"台中清泉岗机场",
            TTT:"台东志航机场",
            TPE:"桃园桃园国际机场",
            KHH:"高雄小岗国际机场",
            PIF:"屏东屏东机场"}
    }

    /*
     执行入口
     */
    var _robotService = new robotService();
    _robotService.serviceInit();
});

//newscript
$.fn.ShowDiv = function() {
    //根据this对象的属性做ajax加载
    var question = $(this).text();
    var requestData = { input:question};
    $.get(chatUrl, requestData, function(data) {
        var answer = data.userAnswerPrefix + data.userAnswerSuffix;
        $("#a_title").html(question);
        $("#a_content").html(answer);
        var width = $(window).width(),
            height = $(window).height(),
            tccdiv = $("#tccdiv"),
            article_width = tccdiv.width(),
            article_height = tccdiv.height();
        $("body").append('<div id="a_Mask" style="position:absolute;left:0px;top:0px;filter:Alpha(Opacity=30);opacity:0.3;background-color:#000000;z-index:99; text-align:center; vertical-align:middle;"></div>');
        $("#a_Mask").css({
            width:width,
            height:height
        }).show();
        $("#tccdiv").css({
            left:(width - article_width) / 2,
            top:(height - article_height) / 2
        }).fadeIn();
    });
};

$.fn.autoScroll = function(options) {
    var defaults = {
            position:"bottom"
        },
        sets = $.extend(defaults, options || {});
    if (sets.position == "top")
        this.scrollTop(0);
    else
        this.scrollTop(this[0].scrollHeight);
};