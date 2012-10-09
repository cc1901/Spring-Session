var ns = function (nsString, register) {
    var namespace = function (namespaceString) {
        var names = namespaceString.split('.');
        var root = window;

        for (var i in names) {
            if (names.hasOwnProperty(i)) {
                root[names[i]] = root[names[i]] || {};
                root = root[names[i]];
            }
        }

        return root;
    };
    register(namespace(nsString));
};

ns('robosay', function (ns) {
    ns.initialize = function () {
        $('#talk-send').on('click', function () {
            var input = $('#talk-input').val();
            var requestData = { input:input};
            $('#talk-input').val('');
            var talkHistory = $('#talk-history').val();
            $('#talk-history').val(talkHistory + '\n' + input);
            $.get('/chat-engine/talk', requestData, function (data) {
                var talkHistory = $('#talk-history').val();
                $('#talk-history').val(talkHistory + '\n' + data);
            });
        });
    };
});

$(function () {
    robosay.initialize();
});
