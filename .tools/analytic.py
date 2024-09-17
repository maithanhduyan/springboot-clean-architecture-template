import os

def collect_files(root_dir, extensions, filenames):
    matches = []
    for root, _, files_in_dir in os.walk(root_dir):
        for filename in files_in_dir:
            if filename.endswith(tuple(extensions)) or filename in filenames:
                filepath = os.path.join(root, filename)
                matches.append(filepath)
    return matches

def write_markdown(files, output_file):
    with open(output_file, 'w', encoding='utf-8') as f:
        for filepath in files:
            f.write(f'Ta có dự án spring boot như sau: \n\n')

            f.write(f'# {filepath}\n\n')
            f.write('```\n')
            with open(filepath, 'r', encoding='utf-8') as file_content:
                f.write(file_content.read())
            f.write('\n```\n\n')


            # f.write(f'Thêm chức năng json restful api vào hệ thống\n\n')

if __name__ == '__main__':
    root_dir = '../'
    output_file = 'project_files.md'
    extensions = ['.java', '.properties', '.html', '.js', '.css', 'yaml']  # Danh sách các phần mở rộng tệp
    filenames = ['pom.xml', 'Dockerfile']  # Danh sách các tên tệp cụ thể
    files = collect_files(root_dir, extensions, filenames)
    write_markdown(files, output_file)
