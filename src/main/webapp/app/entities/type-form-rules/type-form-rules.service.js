(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('TypeFormRules', TypeFormRules);

    TypeFormRules.$inject = ['$resource'];

    function TypeFormRules ($resource) {
        var resourceUrl =  'api/type-form-rules/:id';

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
