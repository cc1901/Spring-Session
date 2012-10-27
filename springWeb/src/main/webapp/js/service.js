/*
 针对机器人客服UI相关的函数
 */
$(function () {
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
        this.serviceResize = function (act) {
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
        this.strLimit = function (obj) {
            if (obj.value.length > self.str_count)
                obj.value = obj.value.substring(0, self.str_count);
            else {
                $("#inputcount").html(self.str_count - obj.value.length);
            }
        }

        /*
         注册事件
         */
        this.serviceEvent = function () {

            //注册Windows的Resize事件
            window.onresize = function () {
                self.serviceResize();
            }

            //限制用户输入字数
            $("#talk-input").bind("keyup keydown change ", function () {
                self.strLimit(this);
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

            var currentTime = function () {
                var date = new Date();
                return date.toDateString();
            };

            var formatContent = function (template, content) {
                return template.replace('$content$', content).replace('$time$', currentTime());
            };

            $('#chat-btn').on('click', function () {
                var input = $('#talk-input').val();
                var requestData = { input:input};
                $('#talk-input').val('');

                $('#main-left .grid-box').append($(formatContent(chatHistoryTemplate, input)));

                $.get('/chat-engine/talk', requestData, function (data) {
                    $('#main-left .grid-box').append($(formatContent(responseHistoryTemplate, data)));
                });
            });

            $('#grid-box').on('click', '.good', function () {
                $('#main-left .grid-box').append($(formatContent(responseForReviewTemplate, "谢谢您的表扬,我会再接再厉！")));
            });

            $('#grid-box').on('click', '.bad', function () {
                $('#main-left .grid-box').append($(formatContent(responseForReviewTemplate, "谢谢您的意见，我会勤奋学习改进自己的不足。")));
            });
        }

        /*
         初始化
         */
        this.serviceInit = function () {
            self.serviceResize("1");
            self.serviceEvent();
        }
    }

    /*
     执行入口
     */
    var _robotService = new robotService();
    _robotService.serviceInit();
});