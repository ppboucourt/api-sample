(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('TypeAdmissionStatusSearch', TypeAdmissionStatusSearch);

    TypeAdmissionStatusSearch.$inject = ['$resource'];

    function TypeAdmissionStatusSearch($resource) {
        var resourceUrl =  'api/_search/type-admission-statuses/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
