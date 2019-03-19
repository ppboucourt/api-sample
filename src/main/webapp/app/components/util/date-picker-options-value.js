(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .value('DatePickerRangeOptions', {
            applyClass: 'btn btn-success',
            timePicker: true,
            timePickerIncrement: 30,
            locale: {
                format: "MM/dd/yyyy hh:mm a",
                separator: "  to  "
            },
            ranges: {
                'Today': [moment().startOf('day'), moment().endOf('day')],
                'Yesterday': [moment().subtract(1, 'days').startOf('day'), moment().subtract(1, 'days').endOf('day')],
                'Last 7 Days': [moment().subtract(6, 'days'), moment()],
                'Last 30 Days': [moment().subtract(29, 'days'), moment()],
                'This Month': [moment().startOf('month'), moment().endOf('month')],
                'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
            }
        });
})();
