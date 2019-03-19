(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('GroupType', GroupType);

    GroupType.$inject = ['$resource'];

    function GroupType ($resource) {
        var resourceUrl =  'api/group-types/:id';

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
