(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('TypeEmployeeSearch', TypeEmployeeSearch);

    TypeEmployeeSearch.$inject = ['$resource'];

    function TypeEmployeeSearch($resource) {
        var resourceUrl =  'api/_search/type-employees/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
