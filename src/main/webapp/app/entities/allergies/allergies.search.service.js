(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('AllergiesSearch', AllergiesSearch);

    AllergiesSearch.$inject = ['$resource'];

    function AllergiesSearch($resource) {
        var resourceUrl =  'api/_search/allergies/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
