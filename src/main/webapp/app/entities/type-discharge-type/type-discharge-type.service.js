(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('TypeDischargeType', TypeDischargeType);

    TypeDischargeType.$inject = ['$resource'];

    function TypeDischargeType ($resource) {
        var resourceUrl =  'api/type-discharge-types/:id';

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
