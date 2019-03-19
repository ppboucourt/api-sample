(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('User', User);

    User.$inject = ['$resource'];

    function User ($resource) {
        var service = $resource('api/users/:login', {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'save': { method:'POST' },
            'update': { method:'PUT' },
            'delete':{ method:'DELETE'},
            'isTwoFactorNeeded': {
                url: 'api/isTwoFactorNeeded/:browserUuid', method: 'GET'
            },
            'sendSmsCode': {
                url: 'api/sendSmsCode/:browserUuid', method: 'GET'
            },
            'rememberMe': {
                url: 'api/rememberMe/', method: 'POST'
            },
            'verifyCode': {
                url: 'api/verifyCode/', method: 'POST'
            },
        });

        return service;
    }
})();
