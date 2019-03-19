(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('TypeEthnicitySearch', TypeEthnicitySearch);

    TypeEthnicitySearch.$inject = ['$resource'];

    function TypeEthnicitySearch($resource) {
        var resourceUrl =  'api/_search/type-ethnicities/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
