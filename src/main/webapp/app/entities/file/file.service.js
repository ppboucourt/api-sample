(function () {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('File', File);

    File.$inject = ['$resource', '$sessionStorage', 'DataUtils'];

    function File($resource, $sessionStorage, DataUtils) {
        var resourceUrl = 'api/files/:id';

        return $resource(resourceUrl, {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'byOwner': {url: 'api/files/owner/:id', method: 'GET', isArray: true},
            'findFile': {
                url: 'api/file/system/:id', transformResponse: function (data) {
                    if (data) {
                        DataUtils.openFile($sessionStorage.typeFile, data);
                    }
                    return data;
                }
            },
            'findFileById': {
                url: 'api/file/dto/:id'
            },
            'update': {method: 'PUT'}
        });
    }
})();
