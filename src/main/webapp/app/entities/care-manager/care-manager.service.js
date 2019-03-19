(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('CareManager', CareManager);

    CareManager.$inject = ['$resource'];

    function CareManager ($resource) {
        var resourceUrl =  'api/care-managers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
