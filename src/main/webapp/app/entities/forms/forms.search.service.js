(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('FormsSearch', FormsSearch);

    FormsSearch.$inject = ['$resource'];

    function FormsSearch($resource) {
        var resourceUrl =  'api/_search/forms/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
