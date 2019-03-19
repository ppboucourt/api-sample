(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('TypePreAdmissionStatusSearch', TypePreAdmissionStatusSearch);

    TypePreAdmissionStatusSearch.$inject = ['$resource'];

    function TypePreAdmissionStatusSearch($resource) {
        var resourceUrl =  'api/_search/type-pre-admission-statuses/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
