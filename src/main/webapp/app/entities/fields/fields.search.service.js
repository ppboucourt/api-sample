(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('FieldsSearch', FieldsSearch);

    FieldsSearch.$inject = ['$resource'];

    function FieldsSearch($resource) {
        var resourceUrl =  'api/_search/fields/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
