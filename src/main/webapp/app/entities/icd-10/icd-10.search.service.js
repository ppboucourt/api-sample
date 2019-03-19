(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('Icd10Search', Icd10Search);

    Icd10Search.$inject = ['$resource'];

    function Icd10Search($resource) {
        var resourceUrl =  'api/_search/icd-10-s/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
